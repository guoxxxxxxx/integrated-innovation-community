package com.iecas.communityauth.controller;


import com.iecas.communityauth.service.AuthRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * (AuthRole)表控制层
 *
 * @author guox
 * @since 2025-02-05 20:35:39
 */
@RestController
@RequestMapping("authRole")
public class AuthRoleController{
    /**
     * 服务对象
     */
    @Autowired
    private AuthRoleService authRoleService;
}

