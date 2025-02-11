package com.iecas.communityuserservice.controller;


import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.feign.AuthServiceFeign;
import com.iecas.communityuserservice.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    public CommonResult test(@RequestParam String token){
        return authServiceFeign.parseToken(token);
    }
}

