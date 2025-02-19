/**
 * @Time: 2025/2/19 16:44
 * @Author: guoxun
 * @File: TbUploadRecord
 * @Description:
 */

package com.iecas.communityfile.table;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Comment;

import java.util.Date;


@Entity
@Table(name = "tb_upload_record")
public class TbUploadRecord {

    @Id
    @Comment("主键")
    @Column(name = "id", nullable = false, unique = true, columnDefinition = "INT8 AUTO_INCREMENT")
    private Long id;

    @Comment("文件名")
    @Column(name = "filename")
    private String filename;

    @Comment("上传用户id")
    @Column(name = "user_id", nullable = false, columnDefinition = "INT8")
    private Long userId;

    @Comment("对应上传文件的id")
    @Column(name = "file_id", columnDefinition = "INT8")
    private Long fileId;

    @Comment("上传开始时间")
    @Column(name = "upload_start_time")
    private Date uploadStartTime;

    @Comment("上传完成时间")
    @Column(name = "upload_achieve_time")
    private Date uploadAchieveTime;

    @Comment("上传状态, FINISH: 完成, UPLOADING: 上传中, FAIL: 上传失败")
    @Column(name = "status")
    private String status;

    @Comment("删除标志位")
    @Column(name = "deleted", columnDefinition = "BOOL DEFAULT FALSE")
    private Boolean deleted;
}
