package com.iecas.communityfile.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.common.UserThreadLocal;
import com.iecas.communitycommon.config.feign.CustomRequestAttributes;
import com.iecas.communitycommon.constant.RedisPrefix;
import com.iecas.communitycommon.constant.TranscodeStatusEnum;
import com.iecas.communitycommon.constant.UploadEnum;
import com.iecas.communitycommon.exception.CommonException;
import com.iecas.communitycommon.feign.VideoServiceFeign;
import com.iecas.communitycommon.model.file.entity.FileInfo;
import com.iecas.communitycommon.model.file.entity.UploadRecord;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import com.iecas.communitycommon.model.video.entity.TranscodeInfo;
import com.iecas.communitycommon.model.video.entity.VideoInfo;
import com.iecas.communitycommon.utils.CommonResultUtils;
import com.iecas.communitycommon.utils.FileUtils;
import com.iecas.communitycommon.utils.ShaUtils;
import com.iecas.communitycommon.utils.VideoUtils;
import com.iecas.communityfile.config.GlobalThreadPools;
import com.iecas.communityfile.dao.UploadInfoDao;
import com.iecas.communityfile.pojo.dto.CoverUploadDTO;
import com.iecas.communityfile.pojo.dto.FileUploadDTO;
import com.iecas.communityfile.pojo.dto.FileUploadMultiBlockDTO;
import com.iecas.communityfile.pojo.dto.FileUploadPreHandleDTO;
import com.iecas.communityfile.pojo.middleEntity.TranscodeResolutionDataAndStatus;
import com.iecas.communityfile.pojo.middleEntity.UploadOtherInfo;
import com.iecas.communityfile.pojo.vo.CheckFileUploadIsOkVO;
import com.iecas.communityfile.pojo.vo.FileUploadPreHandleVO;
import com.iecas.communityfile.service.FfmpegService;
import com.iecas.communityfile.service.FileInfoService;
import com.iecas.communityfile.service.UploadRecordService;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.*;

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

    @Value("${file.default-output-path}")
    private String DEFAULT_OUTPUT_PATH;

    @Value("${file.chunk-size}")
    private Long CHUNK_SIZE;

    @Resource
    private UploadRecordService uploadRecordService;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    VideoServiceFeign videoServiceFeign;

    @Resource
    FfmpegService ffmpegService;


    @Override
    public FileInfo uploadSingleFile(FileUploadDTO dto) throws IOException, NoSuchAlgorithmException {
        FileInfo currnetFileInfo = new FileInfo();
        UploadRecord currentUploadRecord = new UploadRecord();
        MultipartFile file = dto.getFile();

        if (file == null){
            throw new CommonException("请选择所要上传的文件");
        }

        // 设置开始上传时间
        currentUploadRecord.setUploadStartTime(new Date());
        currentUploadRecord.setUserId(UserThreadLocal.getUserInfo().getId());

        // 提取文件名称和文件类型等元数据
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        long fileSize = file.getSize();

        currentUploadRecord.setFilename(fileName);

        // 多层hash校验文件是否已在服务器存在
        // 计算当前文件md5
        String fileMd5 = ShaUtils.getFileMd5(dto.getFile().getInputStream());
        currnetFileInfo.setMd5(fileMd5);

        // 查询数据库中此文件的md5是否存在
        List<FileInfo> suspectFileList = baseMapper.selectList(new LambdaQueryWrapper<FileInfo>()
                .eq(FileInfo::getMd5, fileMd5));
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
        currnetFileInfo.setType(fileType.replace(".", ""));
        currnetFileInfo.setUserId(UserThreadLocal.getUserInfo().getId());

        // 当前文件已经在服务器中存在, 直接设置引用即可
        if (!suspectFileList.isEmpty()){
            FileInfo existFile = suspectFileList.get(0);
            currnetFileInfo.setFileName(existFile.getFileName());
            currnetFileInfo.setSavePath(existFile.getSavePath());
            baseMapper.insert(currnetFileInfo);
            currentUploadRecord.setUploadAchieveTime(new Date());
            currentUploadRecord.setFileId(existFile.getId());
            currentUploadRecord.setStatus("SUCCESS");
            uploadRecordService.save(currentUploadRecord);
            return currnetFileInfo;
        }
        // 若文件不存在, 则对文件进行上传并保存
        else{
            String newFileName = UUID.randomUUID() + fileType;
            currnetFileInfo.setSavePath(DEFAULT_SAVE_PATH + newFileName);
            currnetFileInfo.setFileName(newFileName);
            // 对文件进行存储
            try {
                FileUtils.saveFile(DEFAULT_SAVE_PATH + newFileName, dto.getFile().getInputStream());
                // TODO 此处需要异步计算文件的SHA码, 并进对数据库进行更新 此处暂时按照同步方式进行计算
                if (currnetFileInfo.getSha256() == null){
                    currnetFileInfo.setSha256(ShaUtils.getFileSHA256(dto.getFile().getInputStream()));
                }
                if (currnetFileInfo.getSha512() == null){
                    currnetFileInfo.setSha512(ShaUtils.getFileSHA512(dto.getFile().getInputStream()));
                }
                baseMapper.insert(currnetFileInfo);
                currentUploadRecord.setUploadAchieveTime(new Date());
                currentUploadRecord.setStatus("SUCCESS");
                currentUploadRecord.setFileId(currnetFileInfo.getId());
                uploadRecordService.save(currentUploadRecord);
                return currnetFileInfo;
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                currentUploadRecord.setStatus("FAIL");
                uploadRecordService.save(currentUploadRecord);
                throw new CommonException("文件上传失败");
            }
        }
    }


    /**
     * 更新上传状态
     * @param fileUUID 文件uuid
     * @param chunkId 分块id
     * @return 更新结果
     * TODO 待解决问题
     * 此处应该考虑如果在分布式环境下, 应该需要添加分布式锁.
     * 但又引出问题, 需要采用分布式存储, 如果该项目仅部署一个实体的话, 采用分布式锁又没有意义
     * 故此处后续应该修改为分布式存储框架
     */
    private synchronized Boolean updateUploadStatus(String fileUUID, int chunkId){

        boolean result = false;

        String cacheString = stringRedisTemplate.opsForValue().get(RedisPrefix.FILE_UPLOAD_CHUNK_INFO.getPath(fileUUID));
        FileUploadPreHandleVO cacheEntity = JSON.parseObject(cacheString, FileUploadPreHandleVO.class);
        assert cacheEntity != null;
        if (cacheEntity.getFileChunkInfoList().get(chunkId).getChunkId() == chunkId){
            cacheEntity.getFileChunkInfoList().get(chunkId).setIsOk(true);
            stringRedisTemplate.opsForValue().set(RedisPrefix.FILE_UPLOAD_CHUNK_INFO.getPath(fileUUID),
                    JSON.toJSONString(cacheEntity));
            result = true;
        }
        else {
            for(FileUploadPreHandleVO.FileChunkInfo e : cacheEntity.getFileChunkInfoList()){
                if (e.getChunkId() == chunkId){
                    e.setIsOk(true);
                    stringRedisTemplate.opsForValue().set(RedisPrefix.FILE_UPLOAD_CHUNK_INFO.getPath(fileUUID),
                            JSON.toJSONString(cacheString));
                    result = true;
                    break;
                }
            }
        }
        return result;
    }


    @Override
    public boolean uploadFileMultiBlock(FileUploadMultiBlockDTO dto) throws Exception {

        // 从redis中查询上传状态信息
        String cacheInfo = stringRedisTemplate.opsForValue().get(RedisPrefix.FILE_UPLOAD_CHUNK_INFO.getPath(dto.getFileUUID()));

        FileUploadPreHandleVO cacheEntity = JSON.parseObject(cacheInfo, FileUploadPreHandleVO.class);

        Boolean status = FileUtils.writeWithChunk(cacheEntity.getSavePath(), dto.getFile().getInputStream(),
                dto.getChunkMd5(), dto.getChunkId(), dto.getChunkSize());
        if (status){
            // 更新上传状态
            return updateUploadStatus(dto.getFileUUID(), dto.getChunkId());
        }
        return false;
    }


    @Override
    public FileUploadPreHandleVO fileuploadPreHandle(FileUploadPreHandleDTO dto) throws IOException {

        UserInfo currentUser = UserThreadLocal.getUserInfo();

        // 计算分块数
        int chunkCount = (int)Math.ceil((double) dto.getFileSize() / CHUNK_SIZE);
        // 生成中间信息
        FileUploadPreHandleVO vo = new FileUploadPreHandleVO(chunkCount);

        // 生成记录信息并存入数据库
        UploadRecord record = UploadRecord.builder()
                .filename(dto.getFilename())
                .status("RUNNING")
                .uploadStartTime(new Date())
                .tmpFileSavePath(FileUtils.pathJoin(DEFAULT_SAVE_PATH, "originVideos/", vo.getFileUUID() + ".iic_cache_tmp"))
                .userId(currentUser.getId()).build();
        uploadRecordService.save(record);

        vo.setRecordId(record.getId());
        vo.setMd5(dto.getMd5());
        vo.setOriginFilename(dto.getFilename());
        // 获取文件类型
        String type = dto.getFilename().substring(dto.getFilename().lastIndexOf(".")).replace(".", "");
        vo.setType(type);

        // 设置其他信息
        vo.setOtherInfo(dto.getOtherInfo());

        // 申请文件所需存储空间
        FileUtils.mkdir(FileUtils.pathJoin(DEFAULT_SAVE_PATH, "originVideos/"));
        String savePath = FileUtils.applyingDiskSpace(FileUtils.pathJoin(DEFAULT_SAVE_PATH, "originVideos/"), vo.getFileUUID(), dto.getFileSize());
        vo.setSavePath(savePath);

        // 将生成的中间信息存入至redis中
        stringRedisTemplate.opsForValue().set(RedisPrefix.FILE_UPLOAD_CHUNK_INFO.getPath(vo.getFileUUID()),
                JSON.toJSONString(vo));
        return vo;
    }


    /**
     * 保存文件信息
     * @param vo 中间信息
     * @return 保存结果
     * @throws IOException io异常
     * @throws NoSuchAlgorithmException 获取sha码异常
     */
    private FileInfo saveFileInfo(FileUploadPreHandleVO vo) throws IOException, NoSuchAlgorithmException {
        UserInfo currentUser = UserThreadLocal.getUserInfo();
        FileInputStream fileInputStream = new FileInputStream(vo.getSavePath());
        String sha256 = ShaUtils.getFileSHA256(fileInputStream);
        String sha512 = ShaUtils.getFileSHA512(fileInputStream);
        FileInfo fileInfo = FileInfo.builder().fileName(vo.getNewFilename()).md5(vo.getMd5()).originFileName(vo.getOriginFilename())
                .savePath(vo.getSavePath()).type(vo.getType()).userId(currentUser.getId()).sha256(sha256)
                .sha512(sha512).size(Files.size(Paths.get(vo.getSavePath()))).uploadTime(new Date()).build();
        baseMapper.insert(fileInfo);
        return fileInfo;
    }


    @Override
    public CheckFileUploadIsOkVO checkFileUploadIsOk(String fileUUID) throws IOException, NoSuchAlgorithmException {
        CheckFileUploadIsOkVO resultVO = new CheckFileUploadIsOkVO();
        // 0. 查询redis中的文件上传状态信息,
        String cacheInfo = stringRedisTemplate.opsForValue().get(RedisPrefix.FILE_UPLOAD_CHUNK_INFO.getPath(fileUUID));
        FileUploadPreHandleVO cacheEntity = JSON.parseObject(cacheInfo, FileUploadPreHandleVO.class);
        if (cacheEntity == null) throw new RuntimeException("当前上传记录不存在");
        // 0-1. 初步校验-若初步校验未通过则直接返回需要重传的数据块
        if (!cacheEntity.checkIsOk()){
            resultVO.setStatus(false);
            resultVO.setFailChunksIdList(cacheEntity.getFailChunksList());
            return resultVO;
        }
        // 1. 计算整个文件的md5码，并于之前前端传递过来的整个文件的md5码做对比, 若相同则返回成功, 若不同进入第二步
        // 1-1. 根据文件路径计算文件的md5码
        String newMD5 = FileUtils.calculateMD5(cacheEntity.getSavePath());
        if (!newMD5.equals(cacheEntity.getMd5())){
            throw new CommonException("文件在上传过程中损坏, 请重新上传");
        }
        else {
            // 2. 文件上传成功
            // 2-1. 将文件的扩展名恢复到原始状态
            cacheEntity.resumeFileType();
            // 2-2. 将信息存储至数据库
            FileInfo fileInfo = saveFileInfo(cacheEntity);

            // 根据模式保存相应的其他元信息
            UploadOtherInfo otherInfo = cacheEntity.getOtherInfo();
            UserInfo currentUser = UserThreadLocal.getUserInfo();

            // 判断任务类型
            // 1. 视频任务逻辑
            if (otherInfo.getTaskType().equalsIgnoreCase("VIDEO")){
                double videoDurationSeconds = VideoUtils.getVideoDurationSeconds(cacheEntity.getSavePath());
                // 保存其他元信息至视频数据库
                CommonResult saveVideoResult = videoServiceFeign.save(
                        VideoInfo.builder()
                                .description(otherInfo.getVideoMetadata().getDescription())
                                .fileId(fileInfo.getId())
                                .modifyTime(new Date())
                                .tag(otherInfo.getVideoMetadata().getTags().toString())
                                .title(otherInfo.getVideoMetadata().getTitle())
                                .uploadTime(new Date())
                                .userId(currentUser.getId())
                                .coverUrl(otherInfo.getVideoMetadata().getCoverUrl())
                                .duration(videoDurationSeconds)
                                .categoryId(otherInfo.getVideoMetadata().getCategoryId())
                                .build()
                );
                Long videoId = CommonResultUtils.parseCommonResult(saveVideoResult, Long.class);

                // 创建视频转码任务记录
                TranscodeInfo transcodeInfo = TranscodeInfo.builder()
                        .vid(videoId)
                        .createTime(new Date())
                        .transcodePath(FileUtils.pathJoin(DEFAULT_OUTPUT_PATH, fileUUID))
                        .status(TranscodeStatusEnum.PENDING.getSTATUS())
                        .build();
                CommonResult saveTranscodeInfoCommonResult = videoServiceFeign.saveTranscodeInfo(transcodeInfo);
                Long transcodeId = CommonResultUtils.parseCommonResult(saveTranscodeInfoCommonResult, Long.class);
                transcodeInfo.setId(transcodeId);

                // 异步进行视频转码
                RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
                CustomRequestAttributes customRequestAttributes = new CustomRequestAttributes(requestAttributes);
                GlobalThreadPools.pool.execute(() -> {
                    try {
                        // 更新转码任务的状态信息
                        RequestContextHolder.setRequestAttributes(customRequestAttributes);
                        transcodeInfo.setStatus(TranscodeStatusEnum.RUNNING.getSTATUS());
                        videoServiceFeign.transcodeInfoUpdate(transcodeInfo);
                        TranscodeResolutionDataAndStatus result = ffmpegService.transcodeToHls(cacheEntity.getSavePath(), FileUtils.pathJoin(DEFAULT_OUTPUT_PATH, fileUUID));
                        if (result.isOk()){
                            // 转码成功后更新转码任务状态信息
                            transcodeInfo.setAchievedTime(new Date());
                            transcodeInfo.setStatus(TranscodeStatusEnum.SUCCESS.getSTATUS());
                            // 更新视频信息中的多分辨率播放路径
                            VideoInfo willUpdate = new VideoInfo();
                            willUpdate.setId(videoId);
                            willUpdate.setResolution(JSON.toJSONString(result));
                            videoServiceFeign.updateVideoInfoById(willUpdate);
                            videoServiceFeign.transcodeInfoUpdate(transcodeInfo);
                        }
                        else {
                            // 失败时更新对应人物状态信息
                            transcodeInfo.setStatus(TranscodeStatusEnum.FAIL.getSTATUS());
                            videoServiceFeign.transcodeInfoUpdate(transcodeInfo);
                        }
                    } catch (Exception e) {
                        // 失败时更新对应人物状态信息
                        transcodeInfo.setStatus(TranscodeStatusEnum.FAIL.getSTATUS());
                        videoServiceFeign.transcodeInfoUpdate(transcodeInfo);
                        log.error("视频转码异常", e);
                    } finally {
                        RequestContextHolder.resetRequestAttributes();
                    }
                });

            }

            // 2-3 更新上传记录表
            UploadRecord updateRecord = UploadRecord.builder()
                    .fileId(fileInfo.getId())
                    .status("SUCCESS")
                    .uploadAchieveTime(new Date())
                    .tmpFileStatus(false)
                    .id(cacheEntity.getRecordId()).build();
            uploadRecordService.updateById(updateRecord);

            // 2-4. 删除redis中的缓存
            stringRedisTemplate.delete(RedisPrefix.FILE_UPLOAD_CHUNK_INFO.getPath(fileUUID));
        }

        return CheckFileUploadIsOkVO.builder()
                .failChunksIdList(null)
                .status(true).build();
    }


    @Override
    public String videoCoverUpload(CoverUploadDTO dto) throws IOException {
        // 获取当前登录对象用户信息
        UserInfo currentUser = UserThreadLocal.getUserInfo();

        // 为当前图片生成一个随机的uuid作为当前文件的新名称
        String uuid = UUID.randomUUID().toString();

        // 获取当前图片的类型
        String[] split = dto.getOriginName().split("\\.");
        String type = split[split.length - 1];

        // 创建上传状态信息记录 并 保存
        UploadRecord uploadRecord = UploadRecord.builder()
                .status(UploadEnum.RUNNING.getValue())
                .userId(currentUser.getId())
                .filename(uuid + "." + type)
                .uploadStartTime(new Date())
                .build();
        uploadRecordService.save(uploadRecord);

        // 保存相关元数据信息
        FileInfo currentFileInfo = FileInfo.builder()
                .fileName(uuid + "." + type)
                .md5(dto.getMd5())
                .uploadTime(new Date())
                .userId(currentUser.getId())
                .originFileName(dto.getOriginName())
                .size(dto.getSize())
                .savePath(FileUtils.pathJoin(DEFAULT_SAVE_PATH, uuid + "." + type))
                .type(type)
                .build();

        String currentMd5 = "";
        try {
            currentMd5 = FileUtils.calculateMD5(dto.getFile().getInputStream());
        } catch (Exception e) {
            // 更新上传状态信息为失败
            uploadRecord.setStatus(UploadEnum.FAIL.getValue());
            uploadRecordService.updateById(uploadRecord);
            throw new RuntimeException("文件md5码计算错误");
        }
        if (!currentMd5.isEmpty() && dto.getMd5().equals(currentMd5)){
            // 对文件进行保存
            boolean status = FileUtils.saveFile(FileUtils.pathJoin(DEFAULT_SAVE_PATH, "images", uuid + "." + type),
                    dto.getFile().getInputStream());
            if (status){
                baseMapper.insert(currentFileInfo);
                // 更新上传记录信息表
                uploadRecord.setFileId(currentFileInfo.getId());
                uploadRecord.setStatus(UploadEnum.SUCCESS.getValue());
                uploadRecord.setUploadAchieveTime(new Date());
                uploadRecordService.updateById(uploadRecord);
                return FileUtils.pathJoin(DEFAULT_SAVE_PATH, "images", uuid + "." + type);
            }
            else {
                // 更新上传状态信息为失败
                uploadRecord.setStatus(UploadEnum.FAIL.getValue());
                uploadRecordService.updateById(uploadRecord);
                throw new CommonException("上传失败，请重试!");
            }
        }
        else{
            // 更新上传状态信息为失败
            uploadRecord.setStatus(UploadEnum.FAIL.getValue());
            uploadRecordService.updateById(uploadRecord);
            throw new CommonException("文件md5校验失败，请重新上传");
        }
    }
}

