package com.iecas.communityauth.controller;


import com.iecas.communityauth.dto.LoginDTO;
import com.iecas.communityauth.dto.RegisterDTO;
import com.iecas.communityauth.dto.ResetDTO;
import com.iecas.communityauth.service.AuthUserService;
import com.iecas.communitycommon.aop.annotation.Auth;
import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.exception.CommonException;
import com.iecas.communitycommon.model.auth.vo.TokenVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


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
    @Auth(permitRole = {"ADMIN"})
    public CommonResult test() {
        return new CommonResult().success().message("调用测试接口成功").data("success");
    }


    @Logger("用户注册")
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public CommonResult register(@RequestBody RegisterDTO registerDTO, HttpServletRequest request) {
        authUserService.register(registerDTO, request);
        return new CommonResult().success().message("注册成功");
    }


    @Logger("重置密码")
    @Operation(summary = "重置密码")
    @PostMapping("/reset")
    public CommonResult reset(@RequestBody ResetDTO resetDTO){
        authUserService.reset(resetDTO);
        return new CommonResult().success().message("修改密码成功");
    }


    @Logger("用户登录")
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public CommonResult login(@RequestBody LoginDTO loginDTO){
        String token = authUserService.login(loginDTO);
        return new CommonResult().success().message("登录成功").data("token", token);
    }


    @Logger("验证用户token")
    @Operation(summary = "验证用户token是否正确")
    @PostMapping("/parseToken")
    public CommonResult parseToken(@RequestParam String token){
        TokenVO result = authUserService.parseToken(token);
        return new CommonResult().success().message("方法调用成功").data(result);
    }
}

