package com.iecas.communityuserservice.controller;


import com.iecas.communitycommon.aop.annotation.Auth;
import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.feign.AuthServiceFeign;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import com.iecas.communityuserservice.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * (UserInfo)表控制层
 *
 * @author guox
 * @since 2025-02-05 20:40:53
 */
@RestController
@RequestMapping("/info")
public class UserInfoController{
    /**
     * 服务对象
     */
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    AuthServiceFeign authServiceFeign;

    @Logger("测试")
    @Operation(summary = "测试")
    @PostMapping("/test")
    @Auth(permitRole = {"ADMIN", "USER"})
    public CommonResult test(@RequestParam String token){
        return authServiceFeign.parseToken(token);
    }


    @Logger("通过token查询当前用户信息")
    @Operation(summary = "通过token查询当前用户信息")
    @PostMapping("/queryUserInfoByToken")
    public CommonResult queryUserInfoByToken(@RequestParam String token){
        UserInfo userInfo = userInfoService.queryUserInfoByToken(token);
        return new CommonResult().success().data(userInfo).message("查询成功");
    }


    @Logger("通过用户邮箱查询用户信息")
    @Operation(summary = "通过用户邮箱查询用户信息")
    @GetMapping("/queryUserInfoByEmail")
    public CommonResult queryUserInfoByEmail(@RequestParam String email){
        UserInfo userInfo = userInfoService.queryUserInfoByEmail(email);
        return new CommonResult().success().data(userInfo).message("查询成功");
    }


    @Logger("通过用户id查询用户详细信息")
    @GetMapping("/{id}")
    public CommonResult queryUserInfoById(@PathVariable(name = "id") Long id){
        UserInfo userInfo = userInfoService.getById(id);
        return new CommonResult().data(userInfo).success();
    }


    @Logger("通过用户id列表同时查询多个用户的信息")
    @GetMapping("/queryUserInfoByIds")
    public CommonResult queryUserInfoByIds(@RequestParam List<Long> ids){
        List<UserInfo> userInfoList = userInfoService.queryUserInfoByIds(ids);
        return new CommonResult().data(userInfoList).success();
    }


    @Logger("通过用户id列表同时查询多个用户的实体信息并映射为Map")
    @GetMapping("queryUserInfoByIds2Map")
    public CommonResult queryUserInfoByIds2Map(@RequestParam List<Long> ids){
        Map<Long, UserInfo> userInfoList = userInfoService.queryUserInfoByIds2Map(ids);
        return new CommonResult().data(userInfoList).success();
    }


}

