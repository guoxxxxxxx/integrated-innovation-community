package com.iecas.communitycomment.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iecas.communitycommon.model.comment.entity.VideoCommentReplyInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Time: 2025/8/12 22:28
 * @Author: guox
 * @File: VideoCommentReplyDao
 * @Description:
 */
@Mapper
public interface VideoCommentReplyDao extends BaseMapper<VideoCommentReplyInfo> {
}
