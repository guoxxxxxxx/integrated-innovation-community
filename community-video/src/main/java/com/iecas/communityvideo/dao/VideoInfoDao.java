package com.iecas.communityvideo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.iecas.communitycommon.model.video.entity.VideoInfo;

/**
 * (VideoInfo)表数据库访问层
 *
 * @author guox
 * @since 2025-03-29 15:23:28
 */



@Mapper
public interface VideoInfoDao extends BaseMapper<VideoInfo> {
}

