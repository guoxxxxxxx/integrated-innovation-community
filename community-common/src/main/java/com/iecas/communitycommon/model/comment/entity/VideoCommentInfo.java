package com.iecas.communitycommon.model.comment.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * (VideoComment)表实体类
 *
 * @author guoxun
 * @since 2025-08-12 22:19:45
 */
@TableName("tb_video_comment")
@Data
public class VideoCommentInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主评论id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 回复的父id, 若不存在则为null
     */
    private Long parentId;
    
    /**
     * 用户地址
     */
    private String address;
    
    /**
     * 评论内容
     */
    private String content;
    
    /**
     * 创建时间
     */
    private Long createTime;
    
    /**
     * 删除位
     */
    private Boolean deleted;
    
    /**
     * 点赞数量
     */
    private Long likes;
    
    /**
     * 用户id
     */
    private Long uid;
    
    /**
     * ip地址
     */
    private String ipAddress;
    
    /**
     * ip归属地
     */
    private String ipCity;
    
}

