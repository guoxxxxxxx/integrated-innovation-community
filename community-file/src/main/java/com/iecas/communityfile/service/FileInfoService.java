package com.iecas.communityfile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iecas.communitycommon.model.file.entity.FileInfo;
import com.iecas.communityfile.pojo.dto.CoverUploadDTO;
import com.iecas.communityfile.pojo.dto.FileUploadDTO;
import com.iecas.communityfile.pojo.dto.FileUploadMultiBlockDTO;
import com.iecas.communityfile.pojo.dto.FileUploadPreHandleDTO;
import com.iecas.communityfile.pojo.vo.CheckFileUploadIsOkVO;
import com.iecas.communityfile.pojo.vo.FileUploadPreHandleVO;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * (UploadInfo)表服务接口
 *
 * @author guox
 * @since 2025-02-17 21:29:44
 */
public interface FileInfoService extends IService<FileInfo> {


    /**
     * 上传单个文件
     * @param dto 文件对象
     * @return 写入数据库的信息
     */
    FileInfo uploadSingleFile(FileUploadDTO dto) throws IOException, NoSuchAlgorithmException;


    /**
     * 文件分块上传
     * @param dto 文件对象信息
     * @return 写入数据库的信息
     */
    boolean uploadFileMultiBlock(FileUploadMultiBlockDTO dto) throws Exception;


    /**
     * 文件分块上传预处理
     * @param dto 文件对象信息
     * @return 文件分块上传预处理信息
     */
    FileUploadPreHandleVO fileuploadPreHandle(FileUploadPreHandleDTO dto) throws IOException;


    /**
     * 检查文件是否上传完成
     * @param fileUUID 文件uuid
     * @return 文件上传状态 及失败列表
     */
    CheckFileUploadIsOkVO checkFileUploadIsOk(String fileUUID) throws IOException, NoSuchAlgorithmException, InterruptedException;


    /**
     * 上传视频封面图片
     * @param dto 相关参数
     * @return 图片在服务器中的路径地址
     */
    String videoCoverUpload(CoverUploadDTO dto) throws IOException;
}

