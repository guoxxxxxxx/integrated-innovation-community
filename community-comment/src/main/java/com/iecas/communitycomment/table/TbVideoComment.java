package com.iecas.communitycomment.table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Comment;

import java.util.Date;

/**
 * @Time: 2025/8/12 21:48
 * @Author: guox
 * @File: TbVideoComment
 * @Description: 视频播放界面评论信息
 */
@Entity
@Table(name = "tb_video_comment")
public class TbVideoComment {

    @Comment("主评论id")
    @Id
    @Column(name = "id", columnDefinition = "INT8 AUTO_INCREMENT")
    private Long id;

    @Comment("用户id")
    @Column(name = "uid")
    private Long uid;

    @Comment("ip地址")
    @Column(name = "ip_address", length = 32)
    private String ipAddress;

    @Comment("ip归属地")
    @Column(name = "ip_city")
    private String ipCity;

    @Comment("评论内容")
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Comment("点赞数量")
    @Column(name = "likes")
    private Long likes;

    @Comment("创建时间")
    @Column(name = "create_time")
    private Date createTime;

    @Comment("删除位")
    @Column(name = "deleted")
    private Boolean deleted;

    @Comment("评论所属视频id")
    @Column(name = "vid", nullable = false)
    private Long vid;
}
