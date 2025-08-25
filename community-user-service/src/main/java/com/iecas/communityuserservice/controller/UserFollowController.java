package com.iecas.communityuserservice.controller;

import com.iecas.communitycommon.aop.annotation.Auth;
import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.model.user.entity.UserFollowInfo;
import com.iecas.communityuserservice.service.UserFollowService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


/**
 * @Time: 2025/8/25 15:54
 * @Author: guox
 * @File: UserFollowController
 * @Description:
 */
@RestController
@RequestMapping("/follow")
public class UserFollowController {

    @Resource
    UserFollowService userFollowService;


    @Logger("获取给定用户对指定用户对关注状态")
    @GetMapping("/{sourceId}/{targetId}")
    public CommonResult queryFollowStatus(@PathVariable(name = "sourceId") Long sourceId,
                                          @PathVariable(name = "targetId") Long targetId){
        UserFollowInfo result = userFollowService.queryFollowStatus(sourceId, targetId);
        return new CommonResult().data(result).success();
    }


    @Auth
    @Logger("根据所要关注的用户的id关注用户")
    @PostMapping("/{targetId}")
    public CommonResult followUser(@PathVariable(name = "targetId") Long targetId){
        UserFollowInfo result = userFollowService.followUser(targetId);
        return new CommonResult().data(result).success();
    }


    @Auth
    @Logger("根据所要关注用户的id取消关注用户")
    @PostMapping("/cancelFollow/{targetId}")
    public CommonResult cancelFollow(@PathVariable(name = "targetId") Long targetId){
        UserFollowInfo result = userFollowService.cancelFollow(targetId);
        return new CommonResult().data(result).success();
    }
}
