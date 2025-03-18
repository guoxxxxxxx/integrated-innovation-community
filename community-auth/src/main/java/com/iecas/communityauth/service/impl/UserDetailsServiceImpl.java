/**
 * @Time: 2025/2/8 18:32
 * @Author: guoxun
 * @File: UserDetailsServiceImpl
 * @Description:
 */

package com.iecas.communityauth.service.impl;

import com.alibaba.fastjson2.JSON;
import com.iecas.communityauth.entity.LoginUserInfo;
import com.iecas.communityauth.service.AuthUserService;
import com.iecas.communitycommon.constant.RedisPrefix;
import com.iecas.communitycommon.model.auth.entity.AuthUser;
import com.iecas.communitycommon.utils.MailUtils;
import com.iecas.communitycommon.utils.RandomExpiredTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    AuthUserService authUserService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    /**
     * 从数据库中读取用户信息
     * @param username the username identifying the user whose data is required.
     * @return 用户信息
     * @throws UsernameNotFoundException 用户未查询到异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUserInfo loginUserInfo;

        // load user information
        if (MailUtils.checkEmailIsCorrect(username)) {
            // 缓存中读取用户信息
            String userLoginInfoCache = stringRedisTemplate.opsForValue().get(RedisPrefix.AUTH_EMAIL_MAPPING_LOGIN_USER_INFO.getPath(username));
            if (StringUtils.hasLength(userLoginInfoCache)){
                loginUserInfo = JSON.parseObject(userLoginInfoCache, LoginUserInfo.class);
            }
            else {
                // 从数据库中读取用户信息
                loginUserInfo = authUserService.queryByUserEmail(username);
                stringRedisTemplate.opsForValue().set(RedisPrefix.AUTH_EMAIL_MAPPING_LOGIN_USER_INFO.getPath(username),
                        JSON.toJSONString(loginUserInfo), RandomExpiredTime.getRandomExpiredTime(), TimeUnit.MINUTES);
            }
        } else {
            // 缓存中读取用户信息
            String userLoginInfoCache = stringRedisTemplate.opsForValue().get(RedisPrefix.AUTH_PHONE_MAPPING_LOGIN_USER_INFO.getPath(username));
            if (StringUtils.hasLength(userLoginInfoCache)){
                loginUserInfo = JSON.parseObject(userLoginInfoCache, LoginUserInfo.class);
            }
            else {
                // 从数据库中读取用户信息
                loginUserInfo = authUserService.queryByUserPhone(username);
                stringRedisTemplate.opsForValue().set(RedisPrefix.AUTH_PHONE_MAPPING_LOGIN_USER_INFO.getPath(username),
                        JSON.toJSONString(loginUserInfo), RandomExpiredTime.getRandomExpiredTime(), TimeUnit.MINUTES);
            }
        }

        return loginUserInfo;
    }
}
