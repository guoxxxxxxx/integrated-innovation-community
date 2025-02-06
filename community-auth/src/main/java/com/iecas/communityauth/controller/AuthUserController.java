package com.iecas.communityauth.controller;


import com.iecas.communityauth.service.AuthUserService;
import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "用户状态信息管理", description = "用户状态信息管理相关接口")
public class AuthUserController {

    /**
     * 服务对象
     */
    @Autowired
    private AuthUserService authUserService;


    @Logger("测试")
    @Operation(summary = "测试接口", description = "测试")
    @GetMapping("/test")
    public CommonResult test() {
        return new CommonResult().data("this is test").success();
    }
}

