/**
 * @Time: 2025/2/21 14:25
 * @Author: guoxun
 * @File: FeignResponseEntity
 * @Description:
 */

package com.iecas.communitycommon.entity;


import lombok.Data;



@Data
public class FeignResponseEntity {

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 状态码
     */
    private Integer status;

    /**
     * 错误信息
     */
    private String error;

    /**
     * 堆栈信息
     */
    private String trace;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 请求路径
     */
    private String path;
}
