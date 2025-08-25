package com.iecas.communityvideo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iecas.communitycommon.model.video.entity.VideoCategoryInfo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;

/**
 * @Time: 2025/8/25 20:37
 * @Author: guox
 * @File: VideoCategoryDao
 * @Description:
 */
@Mapper
public interface VideoCategoryDao extends BaseMapper<VideoCategoryInfo> {

    @MapKey("id")
    @Select("SELECT id, category from tb_video_category WHERE deleted = 0")
    HashMap<Long, VideoCategoryInfo> getVideoCategoryMapping();
}
