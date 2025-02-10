package com.iecas.communityauth.controller;


import com.iecas.communityauth.dto.LoginDTO;
import com.iecas.communityauth.dto.RegisterDTO;
import com.iecas.communityauth.dto.ResetDTO;
import com.iecas.communityauth.service.AuthUserService;
import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import com.iecas.communitycommon.exception.CommonException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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
        throw new CommonException("test_exception");
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
        authUserService.login(loginDTO);
        return new CommonResult().success().message("登录成功");
    }
}

