package com.iecas.communityauth.controller;


import com.iecas.communityauth.service.AuthUserService;
import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * (AuthUser)表控制层
 *
 * @author guox
 * @since 2025-02-05 20:35:39
 */
@RestController
@RequestMapping("/user")
public class AuthUserController {

    /**
     * 服务对象
     */
    @Autowired
    private AuthUserService authUserService;


    @Logger("测试")
    @GetMapping("/test")
    public CommonResult test() {
        return new CommonResult().data("this is test").success();
    }
}

