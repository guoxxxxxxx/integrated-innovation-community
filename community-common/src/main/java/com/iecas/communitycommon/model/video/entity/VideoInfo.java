package com.iecas.communitycommon.model.video.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.feign.UserServiceFeign;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import com.iecas.communitycommon.utils.CommonResultUtils;
import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

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
@ToString(exclude = "categoryName")
public class VideoInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
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

    /**
     * 视频所属类别id
     */
    private Long categoryId;

    /**
     * 视频所属类别名称
     */
    @TableField(exist = false)
    private String categoryName;

    /**
     * 点赞数
     */
    private Long likes;

    /**
     * 收藏数
     */
    private Long favorite;

    /**
     * 视频用户信息
     */
    @TableField(exist = false)
    private UserInfo user;

    /**
     * 获取视频封面图片
     */
    @JsonProperty("coverName")
    private String getCoverName(){
        if (this.coverUrl == null){
            return null;
        }
        String[] split = this.coverUrl.split("/");
        return split[split.length - 1];
    }


    /**
     * 视频播放数量+1
     */
    public void viewCountIncrement(){
        this.viewCount++;
    }


    /**
     * 视频类别映射表
     */
    @JsonIgnore
    @TableField(exist = false)
    private HashMap<Long, VideoCategoryInfo> categoryMapping;


    /**
     * 获取类别名称
     */
    public String getCategoryName(){
        if (categoryMapping == null || categoryMapping.isEmpty()){
            return this.categoryName;
        }
        else {
            return categoryMapping.get(this.categoryId).getCategory();
        }
    }
}

