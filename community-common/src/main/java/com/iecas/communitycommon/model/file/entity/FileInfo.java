package com.iecas.communitycommon.model.file.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * (FileInfo)表实体类
 *
 * @author guox
 * @since 2025-02-17 21:29:39
 */
@Data
public class FileInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 删除位
     */
    private Integer deleted;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件大小, 单位bytes
     */
    private Long size;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 上传日期
     */
    private Date uploadTime;

    /**
     * 上传者用户id
     */
    private Long userId;

    /**
     * 文件的md5
     */
    private String md5;

    /**
     * 文件的sha-256
     */
    private String sha256;

    /**
     * 文件的sha-512
     */
    private String sha512;

    /**
     * 原始文件名
     */
    private String originFileName;
}

