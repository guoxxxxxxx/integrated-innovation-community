package com.iecas.communityvideo.controller;



import com.iecas.communitycommon.aop.annotation.Auth;
import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.common.PageResult;
import com.iecas.communitycommon.model.video.entity.VideoInfo;
import com.iecas.communityvideo.pojo.Params.QueryCondition;
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


    @Logger("获取视频列表")
    @GetMapping("/getPage")
    public CommonResult getPage(QueryCondition condition){
        PageResult<VideoInfo> result = videoInfoService.getPage(condition);
        return new CommonResult().success().data(result);
    }


    @Auth
    @Logger("根据id更新视频信息")
    @PutMapping()
    public CommonResult updateById(@RequestBody VideoInfo videoInfo){
        boolean b = videoInfoService.updateById(videoInfo);
        return new CommonResult().data(b).success();
    }


    @Logger("根据id查询视频详细信息")
    @GetMapping("/{id}")
    public CommonResult queryVideoInfoById(@PathVariable(name = "id") Long id){
        VideoInfo videoInfo = videoInfoService.getById(id);
        return new CommonResult().data(videoInfo).success();
    }

}

