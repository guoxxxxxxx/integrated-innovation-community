package com.iecas.communityauth.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iecas.communitycommon.model.auth.entity.AuthPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (AuthPermission)表数据库访问层
 *
 * @author guox
 * @since 2025-02-05 20:24:08
 */
@Mapper
public interface AuthPermissionDao extends BaseMapper<AuthPermission> {

}

