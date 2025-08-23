package com.iecas.communityvideo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iecas.communitycommon.model.video.entity.VideoUserLikesFavorInfo;

/**
 * @Time: 2025/8/23 14:47
 * @Author: guox
 * @File: VideoUserLikesFavorService
 * @Description:
 */
public interface VideoUserLikesFavorService extends IService<VideoUserLikesFavorInfo> {


    /**
     * 根据视频id进行点赞
     * @param videoId 视频id
     * @return 最新结果
     */
    VideoUserLikesFavorInfo likes(Long videoId);


    /**
     * 根据视频id进行取消点赞
     * @param videoId 视频id
     * @return 最新结果
     */
    VideoUserLikesFavorInfo cancelLikes(Long videoId);


    /**
     * 根据视频id进行收藏
     * @param videoId 视频id
     * @return  最新结果
     */
    VideoUserLikesFavorInfo favor(Long videoId);


    /**
     * 根据视频id取消收藏
     * @param videoId 视频id
     * @return 最新结果
     */
    VideoUserLikesFavorInfo cancelFavor(Long videoId);


    /**
     * 获取当前用户关于当前视频的点赞和收藏状况
     * @param videoId 视频id
     * @param userId 用户id
     * @return 最新结果
     */
    VideoUserLikesFavorInfo getVideoLikesAndFavor(Long videoId, Long userId);
}
