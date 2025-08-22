package com.iecas.communityuserservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import com.iecas.communityuserservice.pojo.UserInfoDTO;

import java.util.List;
import java.util.Map;

/**
 * (UserInfo)表服务接口
 *
 * @author guox
 * @since 2025-02-05 20:40:53
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 通过token查询用户的详细信息
     * @param token 用户的token信息
     * @return 用户详细信息
     */
    UserInfo queryUserInfoByToken(String token);


    /**
     * 通过邮箱查询用户信息
     * @param email 用户的邮箱
     * @return 用户信息
     */
    UserInfo queryUserInfoByEmail(String email);


    /**
     * 根据用户id列表同时查询多个用户信息
     * @param ids 用户id列表
     * @return 用户信息列表
     */
    List<UserInfo> queryUserInfoByIds(List<Long> ids);


    /**
     * 根据用户id列表同时查询多个用户信息并将其映射为Map
     * @param ids 用户信息列表
     * @return 用户信息Map
     */
    Map<Long, UserInfo> queryUserInfoByIds2Map(List<Long> ids);


    /**
     * 根据用户id更新用户信息
     * @param dto 用户信息
     * @return 更新后的用户信息
     */
    UserInfo updateUserInfoById(UserInfoDTO dto);
}

