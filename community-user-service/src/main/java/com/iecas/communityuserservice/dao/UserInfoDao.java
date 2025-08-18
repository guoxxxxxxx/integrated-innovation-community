package com.iecas.communityuserservice.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * (UserInfo)表数据库访问层
 *
 * @author guox
 * @since 2025-02-05 20:40:53
 */
@Mapper
public interface UserInfoDao extends BaseMapper<UserInfo> {

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserInfo> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<UserInfo> entities);


    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<UserInfo> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<UserInfo> entities);


    /**
     * 根据用户id批量查询用户信息，返回值为HashMap其中key为用户id，Value为用户实体信息
     * @param ids 用户id列表
     * @return Map
     */
    @MapKey("id")
    Map<Long, UserInfo> selectUserInfoByIds2Map(@Param("ids") List<Long> ids);

}

