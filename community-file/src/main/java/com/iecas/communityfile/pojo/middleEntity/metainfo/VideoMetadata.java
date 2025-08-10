package com.iecas.communityfile.pojo.middleEntity.metainfo;

import lombok.Data;

import java.util.List;

/**
 * @Time: 2025/8/10 13:47
 * @Author: guox
 * @File: VideoMetadata
 * @Description:
 */
@Data
public class VideoMetadata {

    /**
     * 视频标题
     */
    private String title;

    /**
     * 视频描述
     */
    private String description;

    /**
     * 视频标签
     */
    private List<String> tags;

    /**
     * 封面图片路径
     */
    private String coverUrl;
}
