package com.iecas.communitycomment.pojo.params;

import lombok.Data;

/**
 * @Time: 2025/8/16 19:40
 * @Author: guox
 * @File: VideoReplyDTO
 * @Description:
 */
@Data
public class VideoReplyDTO {

    /**
     * 回复内容
     */
    private String content;

    /**
     * 所属评论父id
     */
    private Long parentId;

    /**
     * 所属主评论id
     */
    private Long videoCommentId;
}
