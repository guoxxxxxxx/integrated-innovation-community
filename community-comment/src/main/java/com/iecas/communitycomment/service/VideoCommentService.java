package com.iecas.communitycomment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iecas.communitycomment.pojo.params.QueryCommentParams;
import com.iecas.communitycomment.pojo.params.VideoCommentDTO;
import com.iecas.communitycommon.common.PageResult;
import com.iecas.communitycommon.model.comment.entity.VideoCommentInfo;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @Time: 2025/8/12 22:28
 * @Author: guox
 * @File: VideoCommentService
 * @Description:
 */
public interface VideoCommentService extends IService<VideoCommentInfo> {


    /**
     * 根据视频id查询与之对应的评论信息
     * @param params 查询参数
     * @return 查询结果
     */
    PageResult<VideoCommentInfo> getVideoCommentById(QueryCommentParams params);


    /**
     * 新增一条评论信息
     * @param dto 参数信息
     * @return 是否成功
     */
    boolean saveOneVideComment(VideoCommentDTO dto, HttpServletRequest request);
}
