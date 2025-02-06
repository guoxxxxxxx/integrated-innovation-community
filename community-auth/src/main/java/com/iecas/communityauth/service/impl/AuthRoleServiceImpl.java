package com.iecas.communityauth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communityauth.dao.AuthRoleDao;
import com.iecas.communityauth.service.AuthRoleService;
import com.iecas.communitycommon.model.auth.entity.AuthRole;
import org.springframework.stereotype.Service;

/**
 * (AuthRole)表服务实现类
 *
 * @author guox
 * @since 2025-02-05 20:31:19
 */
@Service("authRoleService")
public class AuthRoleServiceImpl extends ServiceImpl<AuthRoleDao, AuthRole> implements AuthRoleService {

}

