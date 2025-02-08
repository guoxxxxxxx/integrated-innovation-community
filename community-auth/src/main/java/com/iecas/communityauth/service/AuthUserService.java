package com.iecas.communityauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iecas.communityauth.dto.RegisterDTO;
import com.iecas.communitycommon.model.auth.entity.AuthUser;

/**
 * (AuthUser)表服务接口
 *
 * @author guox
 * @since 2025-02-05 20:32:13
 */
public interface AuthUserService extends IService<AuthUser> {

    /**
     * 用户注册
     * @param registerDTO
     */
    void register(RegisterDTO registerDTO);

}

