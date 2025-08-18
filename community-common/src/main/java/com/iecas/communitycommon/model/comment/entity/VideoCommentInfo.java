package com.iecas.communitycommon.model.comment.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (VideoComment)表实体类
 *
 * @author guoxun
 * @since 2025-08-12 22:19:45
 */
@TableName("tb_video_comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoCommentInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主评论id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 评论对应的视频id
     */
    private Long vid;
    
    /**
     * 评论内容
     */
    private String content;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
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

