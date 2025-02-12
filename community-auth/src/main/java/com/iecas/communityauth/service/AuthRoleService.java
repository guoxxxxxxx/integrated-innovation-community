package com.iecas.communityauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iecas.communitycommon.model.auth.entity.AuthRole;

/**
 * (AuthRole)表服务接口
 *
 * @author guox
 * @since 2025-02-05 20:31:19
 */
public interface AuthRoleService extends IService<AuthRole> {


    /**
     * 根据角色ID查询角色名称。
     * 该方法根据传入的角色ID，查询对应的角色名称。
     * 如果查询成功，将返回角色的名称；如果查询失败（如角色ID不存在），返回null
     * @param id 角色的唯一标识符（ID）。该ID应当是数据库中角色的主键。
     * @return 返回与给定ID对应的角色名称。如果找不到角色，返回 `null`
     */
    String queryRoleNameById(Long id);
}

