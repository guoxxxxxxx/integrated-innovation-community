package com.iecas.communityvideo.controller;

import com.iecas.communitycommon.aop.annotation.Auth;
import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.model.video.entity.TranscodeInfo;
import com.iecas.communityvideo.service.TranscodeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @Time: 2025/8/8 21:21
 * @Author: guox
 * @File: TranscodeController
 * @Description: 视频转码服务控制类
 */
@RestController
@RequestMapping("/transcode")
public class TranscodeController {

    @Resource
    private TranscodeService transcodeService;


    @Auth
    @Logger("保存转码记录信息")
    @PostMapping("/saveTranscodeInfo")
    public CommonResult saveTranscodeInfo(@RequestBody TranscodeInfo info){
        boolean status = transcodeService.save(info);
        return new CommonResult().data(info.getId()).success();
    }


    @Auth
    @Logger("根据id更新转码任务信息")
    @PostMapping("/transcodeInfoUpdate")
    public CommonResult transcodeInfoUpdate(@RequestBody TranscodeInfo info){
        boolean b = transcodeService.updateById(info);
        return new CommonResult().data(info).success();
    }
}
