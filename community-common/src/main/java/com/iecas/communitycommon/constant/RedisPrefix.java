package com.iecas.communitycommon.constant;

public enum RedisPrefix {

    /**
     * 注册验证码前缀
     */
    AUTH_CODE_REGISTER("auth:code:register:"),

    /**
     * 登录验证码前缀
     */
    AUTH_CODE_LOGIN("auth:code:login:"),

    /**
     * 找回密码验证码前缀
     */
    AUTH_CODE_RESET("auth:code:reset:"),

    /**
     * 登录token
     */
    AUTH_LOGIN_TOKEN("auth:login:token:"),
    ;


    private final String PREFIX;


    RedisPrefix(String prefix) {
        this.PREFIX = prefix;
    }


    public String getPath(String key) {
        return PREFIX + key;
    }
}
