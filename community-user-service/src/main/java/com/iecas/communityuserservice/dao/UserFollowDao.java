package com.iecas.communityuserservice.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iecas.communitycommon.model.user.entity.UserFollowInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Time: 2025/8/25 15:52
 * @Author: guox
 * @File: UserFollowDao
 * @Description:
 */
@Mapper
public interface UserFollowDao extends BaseMapper<UserFollowInfo> {
}
