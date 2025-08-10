package com.iecas.communitycommon.constant;

/**
 * @Time: 2025/8/4 20:46
 * @Author: guox
 * @File: UploadEnum
 * @Description: 文件上传状态枚举类
 */
public enum UploadEnum {

    // 上传成功
    SUCCESS("SUCCESS"),

    // 上传失败
    FAIL("FAIL"),

    // 等待中
    PENDING("PENDING"),

    // 进行中
    RUNNING("RUNNING")
    ;

    private String STATUS;

    UploadEnum(String status){
        this.STATUS = status;
    }

    public String getValue(){
        return this.STATUS;
    }
}
