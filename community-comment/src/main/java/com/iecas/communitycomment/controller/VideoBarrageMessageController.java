package com.iecas.communitycomment.controller;

import com.iecas.communitycomment.pojo.params.QueryVideoBarrageParams;
import com.iecas.communitycomment.pojo.params.VideoBarrageDTO;
import com.iecas.communitycomment.pojo.vo.VideoBarrageVO;
import com.iecas.communitycomment.service.VideoBarrageMessageService;
import com.iecas.communitycomment.table.MGVideoBarrageMessage;
import com.iecas.communitycommon.aop.annotation.Auth;
import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Time: 2025/8/27 19:39
 * @Author: guox
 * @File: VideoBarrageMessageController
 * @Description:
 */
@RestController
@RequestMapping("/barrage")
public class VideoBarrageMessageController {

    @Resource
    VideoBarrageMessageService videoBarrageMessageService;


    @Logger("根据视频id获取弹幕列表")
    @GetMapping("/{vid}")
    public CommonResult getBarrageMessageByVid(@PathVariable(name = "vid")Long vid){
        List<MGVideoBarrageMessage> result = videoBarrageMessageService.getBarrageMessageByVid(vid);
        return new CommonResult().data(result).success();
    }


    @Auth
    @Logger("根据视频id发送弹幕")
    @PostMapping("")
    public CommonResult saveVideoBarrage(@RequestBody VideoBarrageDTO dto){
        MGVideoBarrageMessage data = videoBarrageMessageService.saveVideoBarrage(dto);
        return new CommonResult().data(data).success();
    }


    @Logger("根据特定条件查询视频弹幕信息")
    @GetMapping()
    public CommonResult getBarrageMessage(QueryVideoBarrageParams params){
        VideoBarrageVO result = videoBarrageMessageService.getBarrageMessage(params);
        return new CommonResult().data(result).success();
    }

}
