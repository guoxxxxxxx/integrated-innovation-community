package com.iecas.communitycommon.model.comment.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * (VideoCommentReply)表实体类
 *
 * @author guoxun
 * @since 2025-08-12 22:23:45
 */
@TableName("tb_video_comment_reply")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoCommentReplyInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 回复内容
     */
    private String content;
    
    /**
     * 回复时间
     */
    private Long createTime;
    
    /**
     * 删除位
     */
    private Boolean deleted;
    
    /**
     * 点赞数
     */
    private Long likes;
    
    /**
     * 所属父评论id
     */
    private Long parentId;
    
    /**
     * 用户
     */
    private Long uid;
    
    /**
     * 所属最顶级父评论id
     */
    private Long videoCommentId;
    
    /**
     * ip地址
     */
    private String ipAddress;
    
    /**
     * ip归属地
     */
    private String ipCity;
    
}

