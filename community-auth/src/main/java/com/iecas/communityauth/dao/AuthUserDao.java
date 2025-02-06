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
    * 批量新增数据（MyBatis原生foreach方法）
    *
    * @param entities List<AuthUser> 实例对象列表
    * @return 影响行数
    */
    int insertBatch(@Param("entities") List<AuthUser> entities);

    /**
    * 批量新增或按主键更新数据（MyBatis原生foreach方法）
    *
    * @param entities List<AuthUser> 实例对象列表
    * @return 影响行数
    * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
    */
    int insertOrUpdateBatch(@Param("entities") List<AuthUser> entities);

}

