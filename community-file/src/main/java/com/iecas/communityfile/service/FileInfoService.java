package com.iecas.communityfile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iecas.communitycommon.model.file.entity.FileInfo;
import com.iecas.communityfile.dto.FileUploadDTO;

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
    FileInfo uploadSingleFile(FileUploadDTO dto);
}

