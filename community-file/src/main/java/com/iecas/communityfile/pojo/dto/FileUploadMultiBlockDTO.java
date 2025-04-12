/**
 * @Time: 2025/3/19 14:59
 * @Author: guoxun
 * @File: FileUploadMultiBlockDTO
 * @Description:
 */

package com.iecas.communityfile.pojo.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class FileUploadMultiBlockDTO {

    /**
     * 文件唯一标识
     */
    private String fileUUID;

    /**
     * 分块大小
     */
    private Long chunkSize;

    /**
     * 当前分块序号
     */
    private Integer chunkId;

    /**
     * 当前分块md5
     */
    private String chunkMd5;

    /**
     * 文件
     */
    private MultipartFile file;
}
