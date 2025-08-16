package com.iecas.communitycomment.pojo.params;

import lombok.Data;

/**
 * @Time: 2025/8/16 17:28
 * @Author: guox
 * @File: VideoCommentDTO
 * @Description:
 */
@Data
public class VideoCommentDTO {


    /**
     * 评论内容
     */
    private String content;


    /**
     * 当前评论所属的视频id
     */
    private Long vid;
}
