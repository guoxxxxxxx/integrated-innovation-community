package com.iecas.communityvideo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communitycommon.common.UserThreadLocal;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import com.iecas.communitycommon.model.video.entity.VideoInfo;
import com.iecas.communitycommon.model.video.entity.VideoUserLikesFavorInfo;
import com.iecas.communityvideo.dao.VideoUserLikesFavorDao;
import com.iecas.communityvideo.service.VideoInfoService;
import com.iecas.communityvideo.service.VideoUserLikesFavorService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Time: 2025/8/23 14:47
 * @Author: guox
 * @File: VideoUserLikesFavorServiceImpl
 * @Description:
 */
@Service("video-user-likes-favor-service")
public class VideoUserLikesFavorServiceImpl extends ServiceImpl<VideoUserLikesFavorDao, VideoUserLikesFavorInfo> implements VideoUserLikesFavorService {

    @Resource
    VideoInfoService videoInfoService;


    @Override
    public VideoUserLikesFavorInfo likes(Long videoId) {
        return likesOrCancelLikes(videoId, true);
    }


    @Override
    public VideoUserLikesFavorInfo cancelLikes(Long videoId) {
        return likesOrCancelLikes(videoId, false);
    }


    @Override
    public VideoUserLikesFavorInfo favor(Long videoId) {
        return favorOrCancelFavor(videoId, true);
    }


    @Override
    public VideoUserLikesFavorInfo cancelFavor(Long videoId) {
        return favorOrCancelFavor(videoId, false);
    }


    @Override
    public VideoUserLikesFavorInfo getVideoLikesAndFavor(Long videoId, Long userId) {
        if (userId == null){
            return VideoUserLikesFavorInfo.builder()
                    .favor(false)
                    .likes(false)
                    .build();
        }
        else {
            boolean exists = baseMapper.exists(new LambdaQueryWrapper<VideoUserLikesFavorInfo>()
                    .eq(VideoUserLikesFavorInfo::getUid, userId)
                    .eq(VideoUserLikesFavorInfo::getVid, videoId));
            if (!exists){
                return VideoUserLikesFavorInfo.builder()
                        .favor(false)
                        .likes(false)
                        .build();
            }
            else {
                return baseMapper.selectOne(new LambdaQueryWrapper<VideoUserLikesFavorInfo>()
                        .eq(VideoUserLikesFavorInfo::getUid, userId)
                        .eq(VideoUserLikesFavorInfo::getVid, videoId));
            }
        }
    }


    private VideoUserLikesFavorInfo favorOrCancelFavor(Long videoId, Boolean isFavor){
        // 获取当前用户信息
        UserInfo currentUser = UserThreadLocal.getUserInfo();
        // 查询当前用户与对应的视频id是否存在
        boolean exists = baseMapper.exists(new LambdaQueryWrapper<VideoUserLikesFavorInfo>()
                .eq(VideoUserLikesFavorInfo::getUid, currentUser.getId())
                .eq(VideoUserLikesFavorInfo::getVid, videoId));

        if (exists){
            VideoUserLikesFavorInfo info = baseMapper.selectOne(new LambdaQueryWrapper<VideoUserLikesFavorInfo>()
                    .eq(VideoUserLikesFavorInfo::getUid, currentUser.getId())
                    .eq(VideoUserLikesFavorInfo::getVid, videoId));
            info.setFavor(isFavor);
            baseMapper.updateById(info);
            long favorCount = checkFavorCountAndUpdate(videoId);
            info.setFavorCount(favorCount);
            return info;
        }
        else {
            VideoUserLikesFavorInfo build = VideoUserLikesFavorInfo.builder()
                    .favor(isFavor)
                    .uid(currentUser.getId())
                    .vid(videoId)
                    .build();
            baseMapper.insert(build);
            long favorCount = checkFavorCountAndUpdate(videoId);
            build.setFavorCount(favorCount);
            return build;
        }
    }


    /**
     * 根据视频id对视频进行点赞 或 取消点赞
     * @param videoId 视频id
     * @param isLikes 是否点赞
     * @return 最新结果
     */
    private VideoUserLikesFavorInfo likesOrCancelLikes(Long videoId, Boolean isLikes){
        // 获取当前用户信息
        UserInfo currentUser = UserThreadLocal.getUserInfo();
        // 查询当前用户与对应的视频id是否存在
        boolean exists = baseMapper.exists(new LambdaQueryWrapper<VideoUserLikesFavorInfo>()
                .eq(VideoUserLikesFavorInfo::getUid, currentUser.getId())
                .eq(VideoUserLikesFavorInfo::getVid, videoId));

        if (exists){
            VideoUserLikesFavorInfo info = baseMapper.selectOne(new LambdaQueryWrapper<VideoUserLikesFavorInfo>()
                    .eq(VideoUserLikesFavorInfo::getUid, currentUser.getId())
                    .eq(VideoUserLikesFavorInfo::getVid, videoId));
            info.setLikes(isLikes);
            baseMapper.updateById(info);
            long likesCount = checkLikesCountAndUpdate(videoId);
            info.setLikesCount(likesCount);
            return info;
        }
        else {
            VideoUserLikesFavorInfo build = VideoUserLikesFavorInfo.builder()
                    .likes(isLikes)
                    .uid(currentUser.getId())
                    .vid(videoId)
                    .build();
            baseMapper.insert(build);
            long likesCount = checkLikesCountAndUpdate(videoId);
            build.setLikesCount(likesCount);
            return build;
        }
    }


    /**
     * 重新计算点赞数量并更新至数据库中
     * @param videoId 视频id
     * @return 点赞数量
     */
    private long checkLikesCountAndUpdate(Long videoId){
        Long likesCount = baseMapper.selectCount(new LambdaQueryWrapper<VideoUserLikesFavorInfo>()
                .eq(VideoUserLikesFavorInfo::getVid, videoId)
                .eq(VideoUserLikesFavorInfo::getLikes, 1));
        // 将喜欢数量更新至数据库中
        VideoInfo videoInfo = videoInfoService.queryVideoInfoById(videoId);
        videoInfo.setLikes(likesCount);
        videoInfoService.updateById(videoInfo);
        return likesCount;
    }


    /**
     * 重新计算收藏数量并更新至数据库中
     * @param videoId 视频id
     * @return 点赞数量
     */
    private long checkFavorCountAndUpdate(Long videoId){
        Long favorCount = baseMapper.selectCount(new LambdaQueryWrapper<VideoUserLikesFavorInfo>()
                .eq(VideoUserLikesFavorInfo::getVid, videoId)
                .eq(VideoUserLikesFavorInfo::getFavor, 1));
        // 将喜欢数量更新至数据库中
        VideoInfo videoInfo = videoInfoService.queryVideoInfoById(videoId);
        videoInfo.setFavorite(favorCount);
        videoInfoService.updateById(videoInfo);
        return favorCount;
    }
}
