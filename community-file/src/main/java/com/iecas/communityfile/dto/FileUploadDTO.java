/**
 * @Time: 2025/2/17 21:40
 * @Author: guoxun
 * @File: FileUploadDTO
 * @Description: 文件上传 dto
 */

package com.iecas.communityfile.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class FileUploadDTO {


    /**
     * 文件
     */
    private MultipartFile file;

    /**
     * 文件需要保存的路径
     */
    private String savePath;

    /**
     * 文件的 md5 码
     */
    private String md5;
}
