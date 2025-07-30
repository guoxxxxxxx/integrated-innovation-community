package com.iecas.communityvideo.table;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Comment;

import java.util.Date;


/**
 * @Time: 2025/7/30 20:29
 * @Author: guox
 * @File: TbTranscodingInfo
 * @Description: 视频转码信息表
 */
@Entity
@Table(name = "tb_transcode_info")
public class TbTranscodeInfo {

    @Id
    @Comment("主键")
    @Column(name = "id", nullable = false, unique = true, columnDefinition = "INT8 AUTO_INCREMENT")
    private Long id;

    @Comment("对应的视频id")
    @Column(name = "vid")
    private Long vid;

    @Comment("转码状态: PENDING 等待中, PROCESSIONG 进行中, COMPLETED 完成, FAILED 失败")
    @Column(name = "status")
    private String status;

    @Comment("转码后的多分辨率路径 JSON格式")
    @Column(name = "transcode_path", columnDefinition = "TEXT")
    private String transcodePath;

    @Comment("创建时间")
    @Column(name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createTime;

    @Comment("完成时间")
    @Column(name = "achieved_time")
    private Date achievedTime;
}
