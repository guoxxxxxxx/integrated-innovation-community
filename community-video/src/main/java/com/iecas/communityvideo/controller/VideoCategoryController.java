package com.iecas.communityvideo.controller;

import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.model.video.entity.VideoCategoryInfo;
import com.iecas.communityvideo.service.VideoCategoryService;
import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Time: 2025/8/25 21:37
 * @Author: guox
 * @File: VideoCategoryController
 * @Description:
 */
@RestController
@RequestMapping("/videoCategory")
public class VideoCategoryController {

    @Resource
    private VideoCategoryService videoCategoryService;


    @Logger("获取视频全部类别")
    @GetMapping()
    public CommonResult getAllVideoClass(){
        List<VideoCategoryInfo> result = videoCategoryService.getAllVideoClass();
        return new CommonResult().data(result).success();
    }
}
