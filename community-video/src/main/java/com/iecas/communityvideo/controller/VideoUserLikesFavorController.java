package com.iecas.communityvideo.controller;

import com.iecas.communitycommon.aop.annotation.Auth;
import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.model.video.entity.VideoUserLikesFavorInfo;
import com.iecas.communityvideo.service.VideoUserLikesFavorService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @Time: 2025/8/23 14:48
 * @Author: guox
 * @File: VideoUserLikesFavorController
 * @Description:
 */
@RestController
@RequestMapping("/videoLikes")
public class VideoUserLikesFavorController {

    @Resource
    VideoUserLikesFavorService videoUserLikesFavorService;


    @Logger("根据视频id和用户id获取指定用户的点赞和收藏状态")
    @GetMapping("/{videoId}/{userId}")
    public CommonResult getVideoLikesAndFavor(@PathVariable(name = "videoId") Long videoId,
                                      @PathVariable(name = "userId", required = false) Long userId){
        VideoUserLikesFavorInfo info = videoUserLikesFavorService.getVideoLikesAndFavor(videoId, userId);
        return new CommonResult().data(info).success();
    }


    @Auth
    @Logger("根据视频id进行点赞")
    @PostMapping("/likes/{videoId}")
    public CommonResult likes(@PathVariable(name = "videoId") Long videoId){
        VideoUserLikesFavorInfo info = videoUserLikesFavorService.likes(videoId);
        return new CommonResult().data(info).success();
    }
    
    
    @Auth
    @Logger("根据视频id取消点赞")
    @PostMapping("/cancelLikes/{videoId}")
    public CommonResult cancelLikes(@PathVariable(name = "videoId") Long videoId){
        VideoUserLikesFavorInfo info = videoUserLikesFavorService.cancelLikes(videoId);
        return new CommonResult().data(info).success();
    }


    @Auth
    @Logger("根据视频id进行收藏")
    @PostMapping("/favor/{videoId}")
    public CommonResult favor(@PathVariable(name = "videoId") Long videoId){
        VideoUserLikesFavorInfo info = videoUserLikesFavorService.favor(videoId);
        return new CommonResult().data(info).success();
    }


    @Auth
    @Logger("根据视频id取消收藏")
    @PostMapping("/cancelFavor/{videoId}")
    public CommonResult cancelFavor(@PathVariable(name = "videoId") Long videoId){
        VideoUserLikesFavorInfo info = videoUserLikesFavorService.cancelFavor(videoId);
        return new CommonResult().data(info).success();
    }
}
