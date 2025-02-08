package com.iecas.communityauth.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iecas.communitycommon.model.auth.entity.AuthRolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (AuthRolePermission)表数据库访问层
 *
 * @author guox
 * @since 2025-02-07 19:02:16
 */
@Mapper
public interface AuthRolePermissionDao extends BaseMapper<AuthRolePermission> {
}

