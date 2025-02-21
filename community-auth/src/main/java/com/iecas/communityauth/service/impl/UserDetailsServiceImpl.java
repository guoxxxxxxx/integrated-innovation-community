/**
 * @Time: 2025/2/8 18:32
 * @Author: guoxun
 * @File: UserDetailsServiceImpl
 * @Description:
 */

package com.iecas.communityauth.service.impl;

import com.iecas.communityauth.entity.LoginUserInfo;
import com.iecas.communityauth.service.AuthUserService;
import com.iecas.communitycommon.model.auth.entity.AuthUser;
import com.iecas.communitycommon.utils.MailUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    AuthUserService authUserService;


    /**
     * 从数据库中读取用户信息
     * @param username the username identifying the user whose data is required.
     * @return 用户信息
     * @throws UsernameNotFoundException 用户未查询到异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUserInfo loginUserInfo;

        // load user information TODO 此处可以添加redis缓存
        if (MailUtils.checkEmailIsCorrect(username)) {
            loginUserInfo = authUserService.queryByUserEmail(username);
        } else {
            loginUserInfo = authUserService.queryByUserPhone(username);
        }

        return loginUserInfo;
    }
}
