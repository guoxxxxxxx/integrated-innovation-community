package com.iecas.communityauth.service;

public interface AuthCommonService {

    /**
     * 向指定邮箱发送验证码
     * @param email 邮箱地址
     * @param mode  模式： register-注册; login-登录; reset-恢复;
     * @param length 验证码长度
     */
    void sendAuthCode(String email, String mode, Integer length);


    /**
     * 根据邮箱验证找回密码对应的验证码是否正确
     * @param email 邮箱
     * @param code 验证码
     * @return true or false
     */
    boolean validAuthCode(String email, String code);

}
