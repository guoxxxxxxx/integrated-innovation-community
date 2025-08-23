package com.iecas.communityvideo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.common.PageResult;
import com.iecas.communitycommon.feign.UserServiceFeign;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import com.iecas.communitycommon.utils.CommonResultUtils;
import com.iecas.communityvideo.dao.VideoInfoDao;
import com.iecas.communitycommon.model.video.entity.VideoInfo;
import com.iecas.communityvideo.pojo.Params.QueryCondition;
import com.iecas.communityvideo.service.VideoInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * (VideoInfo)表服务实现类
 *
 * @author guox
 * @since 2025-03-29 15:23:35
 */



@Service("videoInfoService")
public class VideoInfoServiceImpl extends ServiceImpl<VideoInfoDao, VideoInfo> implements VideoInfoService {

    @Resource
    private UserServiceFeign userServiceFeign;

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

        // 查询每个视频对应的用户的详细信息
        List<Long> allUserId = new ArrayList<>();
        for(VideoInfo e : pageResult.getRecords()){
            allUserId.add(e.getUserId());
        }
        // 查询
        CommonResult commonResult = userServiceFeign.queryUserInfoByIds2Map(allUserId);
        HashMap<Long, UserInfo> userInfoMapping = CommonResultUtils.parseCommonResult(commonResult, new TypeReference<>() {
        });
        for(VideoInfo e : pageResult.getRecords()){
            e.setUser(userInfoMapping.get(e.getUserId()));
        }
        PageResult<VideoInfo> result = new PageResult<>(pageResult);
        return result;
    }

    @Override
    public VideoInfo queryVideoInfoById(Long id) {
        VideoInfo videoInfo = baseMapper.selectById(id);
        videoInfo.viewCountIncrement();
        // 更新数据 TODO 此处需要添加并发控制 但是不添加也无所谓，毕竟不是重要数据，丢了就丢了
        baseMapper.updateById(videoInfo);
        // 查询当前视频对应的用户的信息
        CommonResult commonResult = userServiceFeign.queryUserInfoById(videoInfo.getUserId());
        UserInfo userInfo = CommonResultUtils.parseCommonResult(commonResult, UserInfo.class);
        videoInfo.setUser(userInfo);
        return videoInfo;
    }
}

