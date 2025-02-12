package com.iecas.communityauth.controller;


import com.iecas.communityauth.service.AuthRoleService;
import com.iecas.communitycommon.aop.annotation.Logger;
import com.iecas.communitycommon.common.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * (AuthRole)表控制层
 *
 * @author guox
 * @since 2025-02-05 20:35:39
 */
@RestController
@RequestMapping("/role")
public class AuthRoleController{
    /**
     * 服务对象
     */
    @Autowired
    private AuthRoleService authRoleService;


    @Logger("根据id获取角色名称")
    @Operation(summary = "根据id获取角色名称")
    @GetMapping("/queryRoleNameById")
    public CommonResult queryRoleNameById(@RequestParam(value = "id") Long id){
        String roleName = authRoleService.queryRoleNameById(id);
        return new CommonResult().message("查询成功").success().data(roleName);
    }
}

