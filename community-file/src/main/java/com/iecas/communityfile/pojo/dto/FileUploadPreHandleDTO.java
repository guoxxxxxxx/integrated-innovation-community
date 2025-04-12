/**
 * @Time: 2025/3/19 15:31
 * @Author: guoxun
 * @File: fileUploadPreHandleDTO
 * @Description: 文件上传预处理参数
 */

package com.iecas.communityfile.pojo.dto;


import com.iecas.communityfile.pojo.middleEntity.UploadOtherInfo;
import lombok.Data;



@Data
public class FileUploadPreHandleDTO {

    /**
     * 文件名
     */
    private String filename;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件md5
     */
    private String md5;

    /**
     * 其他信息
     */
    private UploadOtherInfo otherInfo;
}
