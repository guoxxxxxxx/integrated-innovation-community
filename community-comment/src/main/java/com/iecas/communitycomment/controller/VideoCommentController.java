package com.iecas.communitycomment.controller;

import com.iecas.communitycomment.pojo.params.QueryCommentParams;
import com.iecas.communitycomment.pojo.params.VideoCommentDTO;
import com.iecas.communitycomment.service.VideoCommentService;
import com.iecas.communitycommon.aop.annotation.Auth;
import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.common.PageResult;
import com.iecas.communitycommon.model.comment.entity.VideoCommentInfo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

/**
 * @Time: 2025/8/12 22:26
 * @Author: guox
 * @File: VideoCommentController
 * @Description:
 */
@RestController
@RequestMapping("/videoComment")
public class VideoCommentController {


    @Resource
    VideoCommentService videoCommentService;


    @Logger("根据视频id查询对应的评论信息")
    @GetMapping("")
    public CommonResult getVideoCommentByVid(QueryCommentParams params) {
        PageResult<VideoCommentInfo> pageResult = videoCommentService.getVideoCommentById(params);
        return new CommonResult().data(pageResult).success();
    }


    @Auth
    @Logger("发送一条评论信息")
    @PostMapping("")
    public CommonResult saveOneVideComment(@RequestBody VideoCommentDTO dto, HttpServletRequest request){
        boolean status = videoCommentService.saveOneVideComment(dto, request);
        return new CommonResult().data(status).success();
    }
}
