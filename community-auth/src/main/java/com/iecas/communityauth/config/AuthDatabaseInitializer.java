/**
 * @Time: 2025/2/7 18:54
 * @Author: guoxun
 * @File: AuthDatabaseInitializer
 * @Description: 启动时自动检测数据库并初始化角色和权限
 * TODO 此处采用硬编码显然是不合理的, 后续需要修改为从配置文件中读取配置信息
 */

package com.iecas.communityauth.config;

import com.iecas.communityauth.service.AuthPermissionService;
import com.iecas.communityauth.service.AuthRolePermissionService;
import com.iecas.communityauth.service.AuthRoleService;
import com.iecas.communitycommon.model.auth.entity.AuthPermission;
import com.iecas.communitycommon.model.auth.entity.AuthRole;
import com.iecas.communitycommon.model.auth.entity.AuthRolePermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class AuthDatabaseInitializer implements CommandLineRunner {

    @Autowired
    private AuthPermissionService permissionService;

    @Autowired
    private AuthRoleService roleService;

    @Autowired
    private AuthRolePermissionService rolePermissionService;

    @Override
    public void run(String... args) {
        log.info("🔍 正在检测数据库角色和权限数据...");

        if (roleService.count() == 0) {
            log.info("🚀 角色表为空，开始初始化角色数据...");
            insertRoles();
        }

        if (permissionService.count() == 0) {
            log.info("🚀 权限表为空，开始初始化权限数据...");
            insertPermissions();
        }

        if (rolePermissionService.count() == 0) {
            log.info("🚀 角色权限关联表为空，开始初始化角色权限数据...");
            insertRolePermissions();
        }

        log.info("✅ 数据库初始化完成！");
    }

    private void insertRoles() {
        List<AuthRole> roles = Arrays.asList(
                new AuthRole(1L, 0, "超级管理员，拥有所有权限", "SUPER_ADMIN"),
                new AuthRole(2L, 0, "管理员，可以管理用户和角色", "ADMIN"),
                new AuthRole(3L, 0, "普通用户，拥有基本权限", "USER"),
                new AuthRole(4L, 0, "游客，权限受限", "GUEST")
        );
        roleService.saveBatch(roles);
    }

    private void insertPermissions() {
        List<AuthPermission> permissions = Arrays.asList(
                new AuthPermission(1L, 0, "PERMISSION_SUPER_ADMIN", "超级管理员权限"),
                new AuthPermission(2L, 0, "PERMISSION_ADMIN", "管理员权限"),
                new AuthPermission(3L, 0, "PERMISSION_UPLOAD", "上传权限"),
                new AuthPermission(4L, 0, "PERMISSION_COMMENT", "评论权限"),
                new AuthPermission(5L, 0, "PERMISSION_DELETE", "删除权限")
        );
        permissionService.saveBatch(permissions);
    }

    private void insertRolePermissions() {
        HashMap<Long, List<Long>> rolePermissionMap = new HashMap<>();
        rolePermissionMap.put(1L, Arrays.asList(1L, 2L, 3L, 4L, 5L));
        rolePermissionMap.put(2L, Arrays.asList(2L, 3L, 4L, 5L));
        rolePermissionMap.put(3L, Arrays.asList(3L, 4L, 5L));
        rolePermissionMap.put(4L, Arrays.asList());

        for (Long roleId : rolePermissionMap.keySet()) {
            for (Long permissionId : rolePermissionMap.get(roleId)) {
                if (permissionId != null) {
                    rolePermissionService.save(new AuthRolePermission(null, permissionId, roleId));
                }
            }
        }
    }
}
