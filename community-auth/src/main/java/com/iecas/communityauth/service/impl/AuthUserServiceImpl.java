package com.iecas.communityauth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communityauth.dao.AuthUserDao;
import com.iecas.communityauth.dto.RegisterDTO;
import com.iecas.communityauth.service.AuthUserService;
import com.iecas.communitycommon.model.auth.entity.AuthUser;
import org.springframework.stereotype.Service;

/**
 * (AuthUser)表服务实现类
 *
 * @author guox
 * @since 2025-02-05 20:32:13
 */
@Service("authUserService")
public class AuthUserServiceImpl extends ServiceImpl<AuthUserDao, AuthUser> implements AuthUserService {


    @Override
    public void register(RegisterDTO registerDTO) {

    }
}

