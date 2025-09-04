package com.iecas.communityvideo.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.common.PageResult;
import com.iecas.communitycommon.constant.RedisPrefix;
import com.iecas.communitycommon.feign.UserServiceFeign;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import com.iecas.communitycommon.model.video.entity.VideoCategoryInfo;
import com.iecas.communitycommon.utils.CommonResultUtils;
import com.iecas.communityvideo.dao.VideoInfoDao;
import com.iecas.communitycommon.model.video.entity.VideoInfo;
import com.iecas.communityvideo.pojo.Params.QueryCondition;
import com.iecas.communityvideo.service.VideoCategoryService;
import com.iecas.communityvideo.service.VideoInfoService;
import jakarta.annotation.Resource;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Resource
    VideoCategoryService videoCategoryService;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    RedissonClient redissonClient;

    @Override
    public PageResult<VideoInfo> getPage(QueryCondition condition) {
        Page<VideoInfo> pageCondition = new Page<>(condition.getPageNo(), condition.getPageSize());
        Page<VideoInfo> pageResult = baseMapper.selectPage(pageCondition, new LambdaQueryWrapper<VideoInfo>()
                .eq(condition.getClazz() != null, VideoInfo::getClass, condition.getClazz())
                .orderBy(condition.isSortByViewsCount(), !condition.isSortByViewsCountDesc(), VideoInfo::getViewCount)
                .orderBy(condition.isSortByUploadTime(), !condition.isSortByUploadTimeDesc(), VideoInfo::getUploadTime)
                .eq(condition.getUid() != null, VideoInfo::getUserId, condition.getUid())
                .ge(condition.getStartUploadTime() != null, VideoInfo::getUploadTime, condition.getStartUploadTime())
                .le(condition.getEndUploadTime() != null, VideoInfo::getUploadTime, condition.getEndUploadTime())
                .eq(condition.getCategoryId() != null && condition.getCategoryId() != 0, VideoInfo::getCategoryId, condition.getCategoryId()));

        // 查询视频对应的类别信息的映射
        HashMap<Long, VideoCategoryInfo> videoCategoryMapping = videoCategoryService.getVideoCategoryMapping();

        // 查询每个视频对应的用户的详细信息
        List<Long> allUserId = new ArrayList<>();
        for(VideoInfo e : pageResult.getRecords()){
            allUserId.add(e.getUserId());
            e.setCategoryMapping(videoCategoryMapping);
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

        // 查询缓存
        String videoInfoCacheJSON = stringRedisTemplate.opsForValue().get(RedisPrefix.VIDEO_INFO_CACHE.getPath(id));
        if (videoInfoCacheJSON != null && !videoInfoCacheJSON.isEmpty()){
            VideoInfo videoInfoCache = JSON.parseObject(videoInfoCacheJSON, VideoInfo.class);
            // 查询最新的播放量
            Long increment = stringRedisTemplate.opsForValue().increment(RedisPrefix.VIDEO_PLAYS_COUNT.getPath(id));
            // 将变化标志位置为1
            stringRedisTemplate.opsForValue().set(RedisPrefix.VIDEO_PLAYS_COUNT_CHANGE_FLAG.getPath(id), "1");
            videoInfoCache.setViewCount(increment);
            return videoInfoCache;
        }

        // 缓存未命中，添加锁避免缓存击穿
        RLock lock = redissonClient.getLock(RedisPrefix.VIDEO_INFO_CACHE_LOCK.getPath(id));

        try {
            if (lock.tryLock(5, 10, TimeUnit.SECONDS)) {
                // 双查缓存, 防止重复溯源
                String cache = stringRedisTemplate.opsForValue().get(RedisPrefix.VIDEO_INFO_CACHE.getPath(id));
                if (cache != null && !cache.isEmpty()) {
                    VideoInfo videoInfo = JSON.parseObject(cache, VideoInfo.class);
                    Long increment = stringRedisTemplate.opsForValue().increment(RedisPrefix.VIDEO_PLAYS_COUNT.getPath(id));
                    // 将变化标志位置为1
                    stringRedisTemplate.opsForValue().set(RedisPrefix.VIDEO_PLAYS_COUNT_CHANGE_FLAG.getPath(id), "1");
                    if (increment != null && increment != 0L)
                        videoInfo.setViewCount(increment);
                    return videoInfo;
                }

                VideoInfo videoInfo = baseMapper.selectById(id);
                // 查询当前视频对应的用户的信息
                CommonResult commonResult = userServiceFeign.queryUserInfoById(videoInfo.getUserId());
                UserInfo userInfo = CommonResultUtils.parseCommonResult(commonResult, UserInfo.class);
                videoInfo.setUser(userInfo);

                // 查询当前视频对象的类别名称
                VideoCategoryInfo categoryName = videoCategoryService.getById(videoInfo.getCategoryId());
                videoInfo.setCategoryName(categoryName.getCategory());

                // 将查询到的信息保存至缓存中，采用定时清除策略，来保证缓存与数据库的一致性, 采用随机缓存时间来确保缓存不会雪崩
                stringRedisTemplate.opsForValue().set(RedisPrefix.VIDEO_INFO_CACHE.getPath(id),
                        JSON.toJSONString(videoInfo), 40 + (int) (Math.random() * 20), TimeUnit.MINUTES);
                // 将视频播放量也存入到cache中
                stringRedisTemplate.opsForValue().set(RedisPrefix.VIDEO_PLAYS_COUNT.getPath(id),
                        String.valueOf(videoInfo.getViewCount()));
                stringRedisTemplate.opsForValue().increment(RedisPrefix.VIDEO_PLAYS_COUNT.getPath(id));
                // 将变化标志位置为1
                stringRedisTemplate.opsForValue().set(RedisPrefix.VIDEO_PLAYS_COUNT_CHANGE_FLAG.getPath(id), "1");
                return videoInfo;
            }
            else {
                Thread.sleep(100);
                // 查询缓存
                String secondQuery = stringRedisTemplate.opsForValue().get(RedisPrefix.VIDEO_INFO_CACHE.getPath(id));
                if (secondQuery != null && !secondQuery.isEmpty()){
                    VideoInfo videoInfoCache = JSON.parseObject(secondQuery, VideoInfo.class);
                    // 查询最新的播放量
                    Long increment = stringRedisTemplate.opsForValue().increment(RedisPrefix.VIDEO_PLAYS_COUNT.getPath(id));
                    // 将变化标志位置为1
                    stringRedisTemplate.opsForValue().set(RedisPrefix.VIDEO_PLAYS_COUNT_CHANGE_FLAG.getPath(id), "1");
                    videoInfoCache.setViewCount(increment);
                    return videoInfoCache;
                }
                else {
                    return baseMapper.selectById(id);
                }
            }
        } catch (InterruptedException e){
            throw new RuntimeException("获取视频信息失败");
        } finally {
            if (lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }
}

