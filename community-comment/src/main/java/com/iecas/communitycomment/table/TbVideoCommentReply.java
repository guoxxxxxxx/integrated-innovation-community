package com.iecas.communitycomment.table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Comment;

import java.util.Date;

/**
 * @Time: 2025/8/12 22:00
 * @Author: guox
 * @File: TbVideoCommentReply
 * @Description: 视频播放界面的视频回复
 */
@Entity
@Table(name = "tb_video_comment_reply")
public class TbVideoCommentReply {

    @Comment("主键")
    @Id
    @Column(name = "id", columnDefinition = "INT8 AUTO_INCREMENT")
    private Long id;

    @Comment("ip地址")
    @Column(name = "ip_address", length = 32)
    private String ipAddress;

    @Comment("ip归属地")
    @Column(name = "ip_city")
    private String ipCity;

    @Comment("所属父评论id")
    @Column(name = "parent_id")
    private Long parentId;

    @Comment("回复内容")
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Comment("回复时间")
    @Column(name = "create_time")
    private Date createTime;

    @Comment("用户")
    @Column(name = "uid")
    private Long uid;

    @Comment("删除位")
    @Column(name = "deleted", columnDefinition = "TINYINT DEFAULT 0")
    private Boolean deleted;

    @Comment("点赞数")
    @Column(name = "likes", columnDefinition = "INT8 DEFAULT 0")
    private Long likes;

}
