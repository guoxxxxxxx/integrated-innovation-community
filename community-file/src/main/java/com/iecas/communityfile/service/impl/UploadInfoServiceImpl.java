package com.iecas.communityfile.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communitycommon.exception.CommonException;
import com.iecas.communitycommon.model.file.entity.FileInfo;
import com.iecas.communitycommon.utils.FileUtils;
import com.iecas.communityfile.dao.UploadInfoDao;
import com.iecas.communityfile.dto.FileUploadDTO;
import com.iecas.communityfile.service.FileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
public class UploadInfoServiceImpl extends ServiceImpl<UploadInfoDao, FileInfo> implements FileInfoService {

    @Value("${file.default-save-path}")
    private String DEFAULT_SAVE_PATH;


    @Override
    public FileInfo uploadSingleFile(FileUploadDTO dto) {
        MultipartFile file = dto.getFile();

        if (file == null){
            throw new CommonException("请选择所要上传的文件");
        }

        // 提取文件名称和文件类型等元数据
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        long fileSize = file.getSize();



        try {
            FileUtils.saveFile(DEFAULT_SAVE_PATH + UUID.randomUUID(), dto.getFile().getInputStream());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        // TODO 在此处需要实现文件的秒传功能, 计划采用多层哈希的方式是3不同算法的md5校验 如果全部都命中, 则认为当前文件已经上传过


        return null;

    }
}

