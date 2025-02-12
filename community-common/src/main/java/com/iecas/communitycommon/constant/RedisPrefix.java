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

    /**
     * 用户角色对应名称前缀
     */
    AUTH_ID_MAPPING_ROLE("auth:mapping:id2role:"),

    /**
     * 用户token角色信息映射
     */
    USER_TOKEN_MAPPING_USERINFO("user:mapping:token2userinfo:"),
    ;


    private final String PREFIX;


    RedisPrefix(String prefix) {
        this.PREFIX = prefix;
    }


    public String getPath(String key) {
        return PREFIX + key;
    }
}
