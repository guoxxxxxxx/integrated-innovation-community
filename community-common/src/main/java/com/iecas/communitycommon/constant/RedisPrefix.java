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
     * 已经登录用户的token信息
     */
    AUTH_LOGIN_TOKEN("auth:login:token:"),

    /**
     * 验证码发送时间间隙限制
     */
    AUTH_CODE_LIMIT("auth:code:limit:"),

    /**
     * 用户角色对应名称前缀
     */
    AUTH_ID_MAPPING_ROLE("auth:mapping:id2role:"),

    /**
     * 用户邮箱对应登录用户信息
     */
    AUTH_EMAIL_MAPPING_LOGIN_USER_INFO("auth:mapping:email2loginUserInfo:"),

    /**
     * 用户手机号对应登录用户信息
     */
    AUTH_PHONE_MAPPING_LOGIN_USER_INFO("auth:mapping:phone2loginUserInfo:"),

    /**
     * 用户token角色信息映射
     */
    USER_TOKEN_MAPPING_USERINFO("user:mapping:token2userinfo:"),

    /**
     * 文件分块上传信息
     */
    FILE_UPLOAD_CHUNK_INFO("file:upload:chunkInfo:"),

    /**
     * 视频播放量信息缓存前缀
     */
    VIDEO_PLAYS_COUNT("video:plays:count:"),

    /**
     * 视频播发量变化标志位
     */
    VIDEO_PLAYS_COUNT_CHANGE_FLAG("video:plays:count:change:flag:"),

    /**
     * 视频信息缓存
     */
    VIDEO_INFO_CACHE("video:info:cache:"),

    /**
     * 视频信息缓存锁
     */
    VIDEO_INFO_CACHE_LOCK("video:info:cache:lock:")
    ;


    private final String PREFIX;


    RedisPrefix(String prefix) {
        this.PREFIX = prefix;
    }


    public String getPath(String key) {
        return PREFIX + key;
    }

    public String getPath(long key) {
        return PREFIX + String.valueOf(key);
    }
}
