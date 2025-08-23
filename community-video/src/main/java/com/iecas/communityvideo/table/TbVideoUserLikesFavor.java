package com.iecas.communityvideo.table;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Comment;

/**
 * @Time: 2025/8/23 14:28
 * @Author: guox
 * @File: TbVideoUserLikesFavor
 * @Description: 视频点赞收藏与用户的关系表
 */
@TableName("tb_video_user_likes_favor")
@Entity
public class TbVideoUserLikesFavor {

    @Id
    @Comment("主键")
    @Column(name = "id", columnDefinition = "INT8 AUTO_INCREMENT")
    private Long id;

    @Comment("用户id")
    @Column(name = "uid", columnDefinition = "INT8")
    private Long uid;

    @Comment("视频id")
    @Column(name = "vid", columnDefinition = "INT8")
    private Long vid;

    @Comment("是否点赞")
    @Column(name = "likes", columnDefinition = "TINYINT DEFAULT 0")
    private Boolean likes;

    @Comment("是否收藏")
    @Column(name = "favor", columnDefinition = "TINYINT DEFAULT 0")
    private Boolean favor;

    @Comment("删除位")
    @Column(name = "deleted", columnDefinition = "TINYINT DEFAULT 0")
    private Boolean deleted;
}
