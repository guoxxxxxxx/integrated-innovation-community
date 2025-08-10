package com.iecas.communityfile.pojo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Time: 2025/8/4 20:07
 * @Author: guox
 * @File: CoverUploadDTO
 * @Description: 视频封面上传方法参数
 */
@Data
public class CoverUploadDTO {

    /**
     * 图片原始名称
     */
    private String originName;

    /**
     * 图片原始大小 单位 B
     */
    private Long size;

    /**
     * 图片本体
     */
    private MultipartFile file;

    /**
     * 文件md5码
     */
    private String md5;
}
