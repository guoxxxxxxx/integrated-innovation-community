package com.iecas.communityauth.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iecas.communitycommon.model.auth.entity.AuthRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (AuthRole)表数据库访问层
 *
 * @author guox
 * @since 2025-02-05 20:31:17
 */
@Mapper
public interface AuthRoleDao extends BaseMapper<AuthRole> {

}

