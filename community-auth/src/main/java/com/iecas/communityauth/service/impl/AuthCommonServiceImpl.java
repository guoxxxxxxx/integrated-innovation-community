/**
 * @Time: 2025/2/8 15:30
 * @Author: guoxun
 * @File: AuthCommonServiceImpl
 * @Description:
 */

package com.iecas.communityauth.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iecas.communityauth.constant.AuthCodeModeEnum;
import com.iecas.communityauth.service.AuthCommonService;
import com.iecas.communityauth.service.AuthUserService;
import com.iecas.communitycommon.constant.RedisPrefix;
import com.iecas.communitycommon.exception.CommonException;
import com.iecas.communitycommon.model.auth.entity.AuthUser;
import com.iecas.communitycommon.utils.MailUtils;
import com.iecas.communitycommon.utils.RandomAuthCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;



@Slf4j
@Service("authCommonService")
public class AuthCommonServiceImpl implements AuthCommonService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    AuthUserService authUserService;


    /**
     * 自动选择合适的模式对验证码进行保存
     * @param email 邮箱地址
     * @param mode 模式
     * @param authCode 验证码
     */
    private void saveAuthCodeByMode(String email, String mode, String authCode){
        if(mode.equalsIgnoreCase(AuthCodeModeEnum.REGISTER.name())){
            stringRedisTemplate.opsForValue().set(RedisPrefix.AUTH_CODE_REGISTER.getPath(email),
                    authCode, 10, TimeUnit.MINUTES);
        }
        else if (mode.equalsIgnoreCase(AuthCodeModeEnum.LOGIN.name())){
            stringRedisTemplate.opsForValue().set(RedisPrefix.AUTH_CODE_LOGIN.getPath(email),
                    authCode, 10, TimeUnit.MINUTES);
        }
        else if (mode.equalsIgnoreCase(AuthCodeModeEnum.RESET.name())){
            stringRedisTemplate.opsForValue().set(RedisPrefix.AUTH_CODE_RESET.getPath(email),
                    authCode, 10, TimeUnit.MINUTES);
        }
        else {
            throw new RuntimeException("mode must be 'register', 'login' or 'reset'");
        }
    }


    /**
     * 根据mode和邮箱删除验证码
     * @param email 邮箱
     * @param mode 模式
     */
    private void deleteAuthCodeByMode(String email, String mode){
        if(mode.equalsIgnoreCase(AuthCodeModeEnum.REGISTER.name())){
            stringRedisTemplate.delete(RedisPrefix.AUTH_CODE_REGISTER.getPath(email));
        }
        else if (mode.equalsIgnoreCase(AuthCodeModeEnum.LOGIN.name())){
            stringRedisTemplate.delete(RedisPrefix.AUTH_CODE_LOGIN.getPath(email));
        }
        else if (mode.equalsIgnoreCase(AuthCodeModeEnum.RESET.name())){
            stringRedisTemplate.delete(RedisPrefix.AUTH_CODE_RESET.getPath(email));
        }
        else {
            throw new RuntimeException("mode must be register or login or reset");
        }
    }


    @Override
    public void sendAuthCode(String email, String mode, Integer length) {
        String randomAuthCode = RandomAuthCodeUtil.getRandomAuthCode(length);

        // 检测当前用户行为是否合规
        if (mode.equalsIgnoreCase(AuthCodeModeEnum.REGISTER.name())) {
            if (authUserService.exists(new LambdaQueryWrapper<AuthUser>()
                    .eq(AuthUser::getEmail, email))){
                throw new CommonException("当前用户已经被注册");
            }
        }
        else if (mode.equalsIgnoreCase(AuthCodeModeEnum.RESET.name())) {
            if (!authUserService.exists(new LambdaQueryWrapper<AuthUser>()
                    .eq(AuthUser::getEmail, email))){
                throw new CommonException("当前用户尚未注册");
            }
        }
        else if (!mode.equalsIgnoreCase(AuthCodeModeEnum.LOGIN.name())) {
            throw new RuntimeException("mode must be login, reset and register");
        }

        // 从redis中判断当前用户的验证码是否在30s内已经被发送过一次了
        boolean exist = stringRedisTemplate.hasKey(RedisPrefix.AUTH_CODE_LIMIT.getPath(email));
        if (exist){
            // 获取可以发送验证码的时间间隔
            Long expire = stringRedisTemplate.getExpire(RedisPrefix.AUTH_CODE_LIMIT.getPath(email), TimeUnit.SECONDS);
            throw new CommonException("验证码发送频繁, 请 " + expire + " 秒后再试!");
        }

        // 将生成的验证码保存到redis中, 过期时间设置为10分钟
        saveAuthCodeByMode(email, mode, randomAuthCode);

        // 向用户发送验证码
        try {
            MailUtils.sendRandomCode(randomAuthCode, email, "10");
            // 向redis中写入验证码发送信息，声明周期为30秒，避免用户在短时间内重复发送验证码
            stringRedisTemplate.opsForValue().set(RedisPrefix.AUTH_CODE_LIMIT.getPath(email), "limit", 30, TimeUnit.SECONDS);
        } catch (CommonException e) {
            deleteAuthCodeByMode(email, mode);
            throw new CommonException(e.getMessage());
        } catch (Exception e) {
            log.error("邮件发送异常", e);
            deleteAuthCodeByMode(email, mode);
            throw new CommonException("邮件发送异常");
        }
    }


    @Override
    public boolean validAuthCode(String email, String code) {
        String s = stringRedisTemplate.opsForValue().get(RedisPrefix.AUTH_CODE_RESET.getPath(email));
        if (s != null && !s.isEmpty()){
            return s.equalsIgnoreCase(code);
        }
        else{
            return false;
        }
    }
}
