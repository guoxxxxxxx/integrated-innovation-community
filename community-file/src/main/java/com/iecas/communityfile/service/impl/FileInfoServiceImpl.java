package com.iecas.communityfile.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communitycommon.common.UserThreadLocal;
import com.iecas.communitycommon.exception.CommonException;
import com.iecas.communitycommon.model.file.entity.FileInfo;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import com.iecas.communitycommon.utils.FileUtils;
import com.iecas.communitycommon.utils.ShaUtils;
import com.iecas.communityfile.dao.UploadInfoDao;
import com.iecas.communityfile.dto.FileUploadDTO;
import com.iecas.communityfile.service.FileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * (UploadInfo)表服务实现类
 *
 * @author guox
 * @since 2025-02-17 21:29:44
 */
@Service("fileInfoService")
@Slf4j
public class FileInfoServiceImpl extends ServiceImpl<UploadInfoDao, FileInfo> implements FileInfoService {

    @Value("${file.default-save-path}")
    private String DEFAULT_SAVE_PATH;


    @Override
    public FileInfo uploadSingleFile(FileUploadDTO dto) throws IOException, NoSuchAlgorithmException {
        FileInfo currnetFileInfo = new FileInfo();
        MultipartFile file = dto.getFile();

        if (file == null){
            throw new CommonException("请选择所要上传的文件");
        }

        // 提取文件名称和文件类型等元数据
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        long fileSize = file.getSize();

        // 多层hash校验文件是否已在服务器存在
        // 计算当前文件md5
        String fileMd5 = ShaUtils.getFileMd5(dto.getFile().getInputStream());
        currnetFileInfo.setMd5(fileMd5);

        // 查询数据库中此文件的md5是否存在
        List<FileInfo> suspectFileList = baseMapper.selectList(new LambdaQueryWrapper<FileInfo>()
                .eq(FileInfo::getMd5, "fileMd5"));
        // 存在可能相同的文件, 进一步校验
        if (!suspectFileList.isEmpty()){
            // 计算文件sha256
            String fileSHA256 = ShaUtils.getFileSHA256(dto.getFile().getInputStream());
            currnetFileInfo.setSha256(fileSHA256);
            suspectFileList.removeIf(e -> !e.getSha256().equals(fileSHA256) || e.getSize() != fileSize);

            // 若sha256重复，继续校验
            if (!suspectFileList.isEmpty()){
                // 计算文件sha512
                String fileSHA512 = ShaUtils.getFileSHA512(dto.getFile().getInputStream());
                currnetFileInfo.setSha512(fileSHA512);
                suspectFileList.removeIf(e -> !e.getSha512().equals(fileSHA512));
            }
        }

        // 设置当前文件的一些基本信息
        currnetFileInfo.setSize(fileSize);
        currnetFileInfo.setOriginFileName(fileName);
        currnetFileInfo.setUploadTime(new Date());
        currnetFileInfo.setType(fileType);
        currnetFileInfo.setUserId(UserThreadLocal.getUserInfo().getId());

        // 当前文件已经在服务器中存在, 直接设置引用即可
        if (!suspectFileList.isEmpty()){
            FileInfo existFile = suspectFileList.get(0);
            currnetFileInfo.setFileName(existFile.getFileName());
            currnetFileInfo.setSavePath(existFile.getSavePath());
            baseMapper.insert(currnetFileInfo);
            return currnetFileInfo;
        }
        // 若文件不存在, 则对文件进行上传并保存
        else{
            currnetFileInfo.setSavePath(DEFAULT_SAVE_PATH + fileName);
            String newFileName = UUID.randomUUID() + "." + fileType;
            currnetFileInfo.setFileName(newFileName);
            // 对文件进行存储
            try {
                FileUtils.saveFile(DEFAULT_SAVE_PATH + newFileName, dto.getFile().getInputStream());
                baseMapper.insert(currnetFileInfo);

                // TODO 此处需要异步计算文件的SHA码, 并进对数据库进行更新 此处暂时按照同步方式进行计算
                if (currnetFileInfo.getSha256() == null){
                    currnetFileInfo.setSha256(ShaUtils.getFileSHA256(dto.getFile().getInputStream()));
                }
                if (currnetFileInfo.getSha512() == null){
                    currnetFileInfo.setSha512(ShaUtils.getFileSHA512(dto.getFile().getInputStream()));
                }
                return currnetFileInfo;
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new CommonException("文件上传失败");
            }
        }
    }
}

