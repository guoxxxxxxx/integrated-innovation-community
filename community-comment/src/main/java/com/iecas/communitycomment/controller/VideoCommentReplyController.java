package com.iecas.communitycomment.controller;

import com.iecas.communitycomment.pojo.params.VideoReplyDTO;
import com.iecas.communitycomment.service.VideoCommentReplyService;
import com.iecas.communitycommon.aop.annotation.Auth;
import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.model.comment.entity.VideoCommentReplyInfo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Time: 2025/8/16 17:15
 * @Author: guox
 * @File: VideoCommentReplyController
 * @Description:
 */
@RestController
@RequestMapping("/videoReply")
public class VideoCommentReplyController {

    @Resource
    VideoCommentReplyService videoCommentReplyService;


    @Logger("根据评论所属父id查询指定视频下的所有评论")
    @GetMapping("/{parentId}")
    public CommonResult getVideoCommentReplyByVCid(@PathVariable(name = "parentId") Long parentId){
        List<VideoCommentReplyInfo> result = videoCommentReplyService.getVideoCommentReplyByVCid(parentId);
        return new CommonResult().data(result).success();
    }

    @Auth
    @Logger("发送一条回复信息")
    @PostMapping("")
    public CommonResult saveVideoReply(@RequestBody VideoReplyDTO dto, HttpServletRequest request){
        Boolean result = videoCommentReplyService.saveVideoReply(dto, request);
        return new CommonResult().data(result).success();
    }
}
