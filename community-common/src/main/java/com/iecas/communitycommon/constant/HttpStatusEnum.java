package com.iecas.communitycommon.constant;

/**
 * @Time: 2025/7/20 15:03
 * @Author: guox
 * @File: HttpStatusEnum
 * @Description: 请求状态码
 */
public enum HttpStatusEnum {

    // 需要跳转到登陆界面
    AUTH_JUMP_LOGIN(1503),
    // 当前用户无权限
    AUTH_NO_AUTHORITY(5403)


    ;

    private final int statusCode;

    HttpStatusEnum(int statusCode){
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
