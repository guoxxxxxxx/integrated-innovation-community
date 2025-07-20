/**
 * @Time: 2025/2/8 15:30
 * @Author: guoxun
 * @File: AuthCommonController
 * @Description:
 */

package com.iecas.communityauth.controller;


import com.iecas.communityauth.service.AuthCommonService;
import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/common")
@Tag(name = "鉴权公共接口")
public class AuthCommonController {

    @Autowired
    AuthCommonService authCommonService;


    @Logger("向指定邮箱发送验证码")
    @GetMapping("/sendAuthCode")
    @Operation(summary = "001-向指定邮箱发送验证码", description = "模式参数分别代表注册、登录、找回密码")
    public CommonResult sendAuthCode(@Parameter(description = "邮箱") @RequestParam String email,
                                     @Parameter(description = "模式: register, login, reset") @RequestParam String mode,
                                     @Parameter(description = "验证码长度") @RequestParam(required = false, defaultValue = "4") Integer length){
        authCommonService.sendAuthCode(email, mode, length);
        return new CommonResult().message("发送成功").success();
    }
    
    
    @Logger("找回密码--验证验证码是否正确")
    @PostMapping("/validAuthCode/{email}/{code}")
    @Operation(summary = "找回密码--验证验证码是否正确")
    public CommonResult validAuthCode(@Parameter(description = "邮箱") @PathVariable String email,
                                      @Parameter(description = "验证码") @PathVariable String code){
        boolean result = authCommonService.validAuthCode(email, code);
        return new CommonResult().data(result).success();
    }
}
