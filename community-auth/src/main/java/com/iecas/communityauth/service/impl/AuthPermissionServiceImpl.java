package com.iecas.communityauth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communityauth.dao.AuthPermissionDao;
import com.iecas.communityauth.service.AuthPermissionService;
import com.iecas.communitycommon.model.auth.entity.AuthPermission;
import org.springframework.stereotype.Service;

/**
 * (AuthPermission)表服务实现类
 *
 * @author guox
 * @since 2025-02-05 20:24:16
 */
@Service("authPermissionService")
public class AuthPermissionServiceImpl extends ServiceImpl<AuthPermissionDao, AuthPermission> implements AuthPermissionService {

}

