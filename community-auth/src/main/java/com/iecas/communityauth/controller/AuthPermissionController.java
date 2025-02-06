package com.iecas.communityauth.controller;


import com.iecas.communityauth.service.AuthPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * (AuthPermission)表控制层
 *
 * @author guox
 * @since 2025-02-05 20:24:07
 */
@RestController
@RequestMapping("authPermission")
public class AuthPermissionController{
    /**
     * 服务对象
     */
    @Autowired
    private AuthPermissionService authPermissionService;
}

