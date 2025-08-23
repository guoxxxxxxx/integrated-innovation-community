package com.iecas.communityvideo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iecas.communitycommon.model.video.entity.VideoUserLikesFavorInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Time: 2025/8/23 14:46
 * @Author: guox
 * @File: VideoUserLikesFavorDao
 * @Description:
 */
@Mapper
public interface VideoUserLikesFavorDao extends BaseMapper<VideoUserLikesFavorInfo> {
}
