package com.iecas.communityuserservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communitycommon.common.UserThreadLocal;
import com.iecas.communitycommon.exception.CommonException;
import com.iecas.communitycommon.model.user.entity.UserFollowInfo;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import com.iecas.communityuserservice.dao.UserFollowDao;
import com.iecas.communityuserservice.service.UserFollowService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Time: 2025/8/25 15:53
 * @Author: guox
 * @File: UserFollowServiceImpl
 * @Description:
 */
@Service("user-follow-service")
public class UserFollowServiceImpl extends ServiceImpl<UserFollowDao, UserFollowInfo> implements UserFollowService {



    @Override
    public UserFollowInfo queryFollowStatus(Long sourceId, Long targetId) {
        UserFollowInfo userFollowInfo = baseMapper.selectOne(new LambdaQueryWrapper<UserFollowInfo>()
                .eq(UserFollowInfo::getSource, sourceId)
                .eq(UserFollowInfo::getTarget, targetId));
        if (userFollowInfo == null){
            return new UserFollowInfo();
        }
        else {
            return userFollowInfo;
        }
    }


    @Override
    public UserFollowInfo followUser(Long targetId) {
        return followOrCancelFollow(targetId, true);
    }


    @Override
    public UserFollowInfo cancelFollow(Long targetId) {
        return followOrCancelFollow(targetId, false);
    }


    /**
     * 关注或者取消关注指定用户
     * @param targetId 目标用户id
     * @param isFollow 是否关注
     * @return 关注状态
     */
    private UserFollowInfo followOrCancelFollow(Long targetId, boolean isFollow){
        // 获取当前登录用户的信息
        UserInfo currentUser = UserThreadLocal.getUserInfo();
        if (Objects.equals(currentUser.getId(), targetId)){
            throw new CommonException("不能关注自己哦！");
        }
        // 查询当前登录用户与指定用户的关注关系表项是否存在
        UserFollowInfo relation = baseMapper.selectOne(new LambdaQueryWrapper<UserFollowInfo>()
                .eq(UserFollowInfo::getSource, currentUser.getId())
                .eq(UserFollowInfo::getTarget, targetId));
        if (relation != null){
            relation.setStatus(isFollow);
            baseMapper.updateById(relation);
            return relation;
        }
        else {
            UserFollowInfo newObject = UserFollowInfo.builder()
                    .source(currentUser.getId())
                    .target(targetId)
                    .status(isFollow)
                    .build();
            baseMapper.insert(newObject);
            return newObject;
        }
    }
}
