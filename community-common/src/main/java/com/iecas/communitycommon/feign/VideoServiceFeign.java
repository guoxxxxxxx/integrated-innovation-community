package com.iecas.communitycommon.feign;


import com.iecas.communitycommon.aop.annotation.Auth;
import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.config.feign.CommonFeignConfig;
import com.iecas.communitycommon.model.video.entity.TranscodeInfo;
import com.iecas.communitycommon.model.video.entity.VideoInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@FeignClient(name = "community-video-service", configuration = {CommonFeignConfig.class})
public interface VideoServiceFeign {

    /**
     * 保存视频信息
     * @param videoInfo 视频信息
     * @return 视频信息的主键
     */
    @PostMapping("/video/video/save")
    CommonResult save(@RequestBody VideoInfo videoInfo);


    /**
     * 保存转码任务信息
     * @param info 转码任务信息
     * @return 任务id
     */
    @PostMapping("/video/transcode/saveTranscodeInfo")
    CommonResult saveTranscodeInfo(@RequestBody TranscodeInfo info);


    /**
     * 根据转码任务id更新转码任务信息
     * @param info 转码任务实体
     * @return 更新后的实体
     */
    @PostMapping("/video/transcode/transcodeInfoUpdate")
    CommonResult transcodeInfoUpdate(@RequestBody TranscodeInfo info);
}
