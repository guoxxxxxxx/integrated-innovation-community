package com.iecas.communitycommon.model.auth.entity;


import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * (AuthRolePermission)表实体类
 *
 * @author guox
 * @since 2025-02-07 19:03:31
 */
@SuppressWarnings("serial")
@AllArgsConstructor
@Data
public class AuthRolePermission{

    /**
     * 主键
     */
    private Long id;

    /**
     * 权限id
     */
    private Long permissionId;

    /**
     * 角色id
     */
    private Long roleId;
}

