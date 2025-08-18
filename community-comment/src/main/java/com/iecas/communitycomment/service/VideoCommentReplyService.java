package com.iecas.communitycomment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iecas.communitycomment.pojo.params.VideoReplyDTO;
import com.iecas.communitycommon.model.comment.entity.VideoCommentReplyInfo;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @Time: 2025/8/12 22:30
 * @Author: guox
 * @File: VideoCommentReplyService
 * @Description:
 */
public interface VideoCommentReplyService extends IService<VideoCommentReplyInfo> {

    /**
     * 根据父评论id查询所有所属的子评论
     * @param parentId 父评论id
     * @return 查询结果
     */
    List<VideoCommentReplyInfo> getVideoCommentReplyByVCid(Long parentId);


    /**
     * 发送视频回复
     * @param dto 视频回复参数
     * @return 状态
     */
    Boolean saveVideoReply(VideoReplyDTO dto,  HttpServletRequest request);
}
