package com.iecas.communityvideo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iecas.communitycommon.model.video.entity.TranscodeInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Time: 2025/8/8 21:19
 * @Author: guox
 * @File: TranscodeDao
 * @Description:
 */
@Mapper
public interface TranscodeDao extends BaseMapper<TranscodeInfo> {
}
