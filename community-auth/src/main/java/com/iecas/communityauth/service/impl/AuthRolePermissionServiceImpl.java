package com.iecas.communityauth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communityauth.dao.AuthRolePermissionDao;
import com.iecas.communityauth.service.AuthRolePermissionService;
import com.iecas.communitycommon.model.auth.entity.AuthRolePermission;
import org.springframework.stereotype.Service;

/**
 * (AuthRolePermission)表服务实现类
 *
 * @author guox
 * @since 2025-02-07 19:02:17
 */
@Service("authRolePermissionService")
public class AuthRolePermissionServiceImpl extends ServiceImpl<AuthRolePermissionDao, AuthRolePermission> implements AuthRolePermissionService {

}

