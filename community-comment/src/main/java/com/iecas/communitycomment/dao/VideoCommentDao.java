package com.iecas.communitycomment.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iecas.communitycomment.pojo.vo.VideoCommentVO;
import com.iecas.communitycommon.model.comment.entity.VideoCommentInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Time: 2025/8/12 22:27
 * @Author: guox
 * @File: VideoCommentDao
 * @Description:
 */
@Mapper
public interface VideoCommentDao extends BaseMapper<VideoCommentInfo> {

}
