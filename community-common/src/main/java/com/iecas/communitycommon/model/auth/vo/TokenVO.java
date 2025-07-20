/**
@Time: 2025/2/12 12:47
@Author: guoxun
@File: TokenVO
@Description: 
*/

package com.iecas.communitycommon.model.auth.vo;


import lombok.Data;



@Data
public class TokenVO {

    /**
     * 消息
     */
    private String message;

    /**
     * 解析状态
     */
    private Boolean status;

    /**
     * 信息体
     */
    private Object parsedData;

    /**
     * http状态码
     */
    private int httpCode;
}
