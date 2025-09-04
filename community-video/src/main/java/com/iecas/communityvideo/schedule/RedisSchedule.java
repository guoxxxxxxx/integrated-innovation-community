package com.iecas.communityvideo.schedule;

import com.iecas.communitycommon.constant.RedisPrefix;
import com.iecas.communitycommon.model.video.entity.VideoInfo;
import com.iecas.communityvideo.service.VideoInfoService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Time: 2025/9/3 20:45
 * @Author: guox
 * @File: RedisSchedule
 * @Description:
 */
@Slf4j
@Component
public class RedisSchedule {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    VideoInfoService videoInfoService;


    /**
     * 刷新缓存信息至数据库, 每五分钟执行一次
     */
    @Scheduled(fixedRate = 300000)
    public void flushVideoViewCount2DB(){
        log.info("开始将视频播放数量进行刷盘处理...");
        Set<String> keys = stringRedisTemplate.keys(RedisPrefix.VIDEO_PLAYS_COUNT_CHANGE_FLAG.getPath("*"));

        // 更新数据库 并 删除redis中变化标志位中的数据
        ScanOptions options = ScanOptions.scanOptions()
                                .match(RedisPrefix.VIDEO_PLAYS_COUNT_CHANGE_FLAG.getPath("*"))
                                .count(1000).build();
        Cursor<byte[]> cursor = stringRedisTemplate.getConnectionFactory().getConnection().scan(options);

        List<String> deletedKeys = new ArrayList<>();
        List<VideoInfo> updateList = new ArrayList<>();

        long updateCount = 0L;
        while (cursor.hasNext()){
            String key = new String(cursor.next());
            String[] split = key.split(":");
            long vid = Long.parseLong(split[split.length - 1]);
            // 查询视频的最新播放量
            String s = stringRedisTemplate.opsForValue().get(RedisPrefix.VIDEO_PLAYS_COUNT.getPath(vid));
            long newCount = Long.parseLong(s);
            VideoInfo update = VideoInfo.builder()
                    .viewCount(newCount)
                    .id(vid)
                    .build();
            updateList.add(update);
            updateCount++;
            deletedKeys.add(key);
        }
        // 更新数据库
        videoInfoService.updateBatchById(updateList);
        // 删除key列表
        stringRedisTemplate.delete(deletedKeys);
        log.info("已将缓存中的数据保存至数据库中, 本次共保存 {} 个数据", updateCount);
    }
}
