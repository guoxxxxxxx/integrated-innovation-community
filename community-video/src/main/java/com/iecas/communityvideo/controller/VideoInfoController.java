package com.iecas.communityvideo.controller;



import com.iecas.communitycommon.aop.annotation.Auth;
import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.model.video.entity.VideoInfo;
import com.iecas.communityvideo.service.VideoInfoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * (VideoInfo)表控制层
 *
 * @author guox
 * @since 2025-03-29 15:23:26
 */



@RestController
@RequestMapping("/video")
public class VideoInfoController {


    /**
     * 服务对象
     */
    @Autowired
    private VideoInfoService videoInfoService;


    @Logger("保存视频信息")
    @Operation(summary = "保存视频信息")
    @Auth
    @PostMapping("/save")
    public CommonResult save(@RequestBody VideoInfo videoInfo){
        boolean status = videoInfoService.save(videoInfo);
        return new CommonResult().success().data(videoInfo.getId());
    }
}

