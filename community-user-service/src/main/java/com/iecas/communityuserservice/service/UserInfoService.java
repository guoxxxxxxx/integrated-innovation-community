package com.iecas.communityuserservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iecas.communitycommon.model.user.entity.UserInfo;

/**
 * (UserInfo)表服务接口
 *
 * @author guox
 * @since 2025-02-05 20:40:53
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 通过token查询用户的详细信息
     * @param token 用户的token信息
     * @return 用户详细信息
     */
    UserInfo queryUserInfoByToken(String token);
}

