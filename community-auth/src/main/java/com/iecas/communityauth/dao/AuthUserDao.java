package com.iecas.communityauth.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iecas.communitycommon.model.auth.entity.AuthUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (AuthUser)表数据库访问层
 *
 * @author guox
 * @since 2025-02-05 20:32:13
 */
@Mapper
public interface AuthUserDao extends BaseMapper<AuthUser> {


    /**
     * 根据用户id查询用户的权限列表
     * @param id 用户id
     * @return {@link List<String>} 用户权限列表
     */
    List<String> queryUserPermissionsById(Long id);
}

