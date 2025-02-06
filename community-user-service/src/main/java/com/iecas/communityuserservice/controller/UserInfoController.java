package com.iecas.communityuserservice.controller;


import com.iecas.communityuserservice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * (UserInfo)表控制层
 *
 * @author guox
 * @since 2025-02-05 20:40:53
 */
@RestController
@RequestMapping("userInfo")
public class UserInfoController{
    /**
     * 服务对象
     */
    @Autowired
    private UserInfoService userInfoService;
}

