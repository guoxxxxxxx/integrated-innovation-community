package com.iecas.communitycommon.feign;


import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.config.feign.CommonFeignConfig;
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
     * @return 保存状态
     */
    @PostMapping("/video/video/save")
    CommonResult save(@RequestBody VideoInfo videoInfo);
}
