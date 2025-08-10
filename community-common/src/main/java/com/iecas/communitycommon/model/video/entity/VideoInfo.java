package com.iecas.communitycommon.model.video.entity;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * (VideoInfo)表实体类
 *
 * @author guox
 * @since 2025-03-29 15:23:30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
    
    /**
     * 删除位
     */
    private Integer deleted;
    
    /**
     * 视频描述
     */
    private String description;
    
    /**
     * 对应的文件的id
     */
    private Long fileId;
    
    /**
     * 修改时间
     */
    private Date modifyTime;
    
    /**
     * 视频标签
     */
    private String tag;
    
    /**
     * 标题
     */
    private String title;
    
    /**
     * 上传时间
     */
    private Date uploadTime;
    
    /**
     * 上传用户id
     */
    private Long userId;

    /**
     * 封面图片
     */
    private String coverUrl;

    /**
     * 视频时长/秒
     */
    private Double duration;

    /**
     * 播放量
     */
    private Long viewCount;

    /**
     * 视频分辨率及播放路径, 采用JSON格式进行存储
     */
    private String resolution;

}

