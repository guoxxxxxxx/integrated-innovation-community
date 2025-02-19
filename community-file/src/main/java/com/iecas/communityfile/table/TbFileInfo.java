/**
 * @Time: 2025/2/17 20:55
 * @Author: guoxun
 * @File: TbUploadInfo
 * @Description:
 */

package com.iecas.communityfile.table;


import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.util.Date;


@Entity
@Table(
        name = "tb_file_info",
        indexes = {
                @Index(name = "idx_md5_sha256", columnList = "md5, sha256")
        }
)
public class TbFileInfo {

    @Id
    @Comment("主键")
    @Column(name = "id", nullable = false, unique = true, columnDefinition = "INT8 AUTO_INCREMENT")
    private Long id;

    @Comment("上传者用户id")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Comment("文件名")
    @Column(name = "file_name")
    private String fileName;

    @Comment("原始的文件名称")
    @Column(name = "origin_file_name")
    private String originFileName;

    @Comment("文件类型")
    @Column(name = "type")
    private String type;

    @Comment("文件大小, 单位bytes")
    @Column(name = "size")
    private Long size;

    @Comment("上传日期")
    @Column(name = "upload_time")
    private Date uploadTime;

    @Comment("删除位")
    @Column(name = "deleted", columnDefinition = "BOOL DEFAULT FALSE")
    private Boolean deleted;

    @Comment("文件MD5")
    @Column(name = "md5", length = 130)
    private String md5;

    @Comment("文件sha-256")
    @Column(name = "sha256", length = 260)
    private String sha256;

    @Comment("文件sha-512")
    @Column(name = "sha512", length = 520)
    private String sha512;
}
