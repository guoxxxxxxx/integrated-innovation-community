package com.iecas.communitycommon.constant;

import lombok.Getter;

/**
 * @Time: 2025/8/8 21:13
 * @Author: guox
 * @File: TranscodeStatusEnum
 * @Description: 转码状态枚举
 */
@Getter
public enum TranscodeStatusEnum {

    // 成功
    SUCCESS("SUCCESS"),

    // 进行中
    RUNNING("RUNNING"),

    // 失败
    FAIL("FAIL"),

    // 等待中
    PENDING("PENDING")
    ;


    private final String STATUS;

    TranscodeStatusEnum(String status){
        this.STATUS = status;
    }
}
