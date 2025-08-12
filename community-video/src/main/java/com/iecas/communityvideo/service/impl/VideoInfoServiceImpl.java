package com.iecas.communityvideo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communitycommon.common.PageResult;
import com.iecas.communityvideo.dao.VideoInfoDao;
import com.iecas.communitycommon.model.video.entity.VideoInfo;
import com.iecas.communityvideo.pojo.Params.QueryCondition;
import com.iecas.communityvideo.service.VideoInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (VideoInfo)表服务实现类
 *
 * @author guox
 * @since 2025-03-29 15:23:35
 */



@Service("videoInfoService")
public class VideoInfoServiceImpl extends ServiceImpl<VideoInfoDao, VideoInfo> implements VideoInfoService {

    @Override
    public PageResult<VideoInfo> getPage(QueryCondition condition) {

        Page<VideoInfo> pageCondition = new Page<>(condition.getPageNo(), condition.getPageSize());
        Page<VideoInfo> pageResult = baseMapper.selectPage(pageCondition, new LambdaQueryWrapper<VideoInfo>()
                .eq(condition.getClazz() != null, VideoInfo::getClass, condition.getClazz())
                .orderBy(condition.isSortByViewsCount(), !condition.isSortByViewsCountDesc(), VideoInfo::getViewCount)
                .orderBy(condition.isSortByUploadTime(), !condition.isSortByUploadTimeDesc(), VideoInfo::getUploadTime)
                .eq(condition.getUid() != null, VideoInfo::getUserId, condition.getUid())
                .ge(condition.getStartUploadTime() != null, VideoInfo::getUploadTime, condition.getStartUploadTime())
                .le(condition.getEndUploadTime() != null, VideoInfo::getUploadTime, condition.getEndUploadTime()));
        PageResult<VideoInfo> result = new PageResult<>(pageResult);
        return result;
    }
}

