package com.iecas.communitycommon.model.video.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Time: 2025/8/23 14:38
 * @Author: guox
 * @File: VideoUserLikesFavor
 * @Description:
 */
@TableName("tb_video_user_likes_favor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoUserLikesFavorInfo {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 视频id
     */
    private Long vid;

    /**
     * 是否点赞
     */
    private Boolean likes;

    /**
     * 是否收藏
     */
    private Boolean favor;

    /**
     * 删除位
     */
    private Boolean deleted;

    /**
     * 当前视频点赞总数
     */
    @TableField(exist = false)
    private Long likesCount;

    /**
     * 当前视频收藏总数
     */
    @TableField(exist = false)
    private Long favorCount;
}
