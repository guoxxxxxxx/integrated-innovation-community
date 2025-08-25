package com.iecas.communityuserservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iecas.communitycommon.model.user.entity.UserFollowInfo;

/**
 * @Time: 2025/8/25 15:53
 * @Author: guox
 * @File: UserFollowService
 * @Description:
 */
public interface UserFollowService extends IService<UserFollowInfo> {

    /**
     * 查询指定用户对关注状态
     * @param sourceId 源用户id
     * @param targetId 目标用户id
     * @return 关注状态
     */
    UserFollowInfo queryFollowStatus(Long sourceId, Long targetId);


    /**
     * 根据指定用户的id关注用户
     * @param targetId 指定用户的id
     * @return 关注状态
     */
    UserFollowInfo followUser(Long targetId);


    /**
     * 根据指定用户的id取消关注用户
     * @param targetId 指定用户的id
     * @return 关注状态
     */
    UserFollowInfo cancelFollow(Long targetId);
}
