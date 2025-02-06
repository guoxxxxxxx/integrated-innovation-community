package com.iecas.communityuserservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iecas.communitycommon.model.user.entity.UserInfo;
import com.iecas.communityuserservice.dao.UserInfoDao;
import com.iecas.communityuserservice.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * (UserInfo)表服务实现类
 *
 * @author guox
 * @since 2025-02-05 20:40:53
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfo> implements UserInfoService {

}

