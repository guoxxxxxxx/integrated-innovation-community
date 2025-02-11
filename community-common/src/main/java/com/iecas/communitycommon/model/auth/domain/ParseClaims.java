/**
 * @Time: 2025/2/11 21:37
 * @Author: guoxun
 * @File: Claims
 * @Description: 解析token信息对象
 */

package com.iecas.communitycommon.model.auth.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iecas.communitycommon.utils.DateTimeUtils;
import lombok.Data;



@Data
public class ParseClaims {

    /**
     * UUID 当前token的唯一识别码
     */
    private String jti;

    /**
     * 签发人
     */
    private String iss;

    /**
     * 主题
     */
    private String sub;

    /**
     * 签发时间
     */
    private Long iat;

    /**
     * 过期时间
     */
    private Long exp;


    /**
     * 获取格式化后的签发时间
     * @return YY-MM-dd hh:mm:ss
     */
    @JsonIgnore
    private String getFormatIat(){
        return DateTimeUtils.getFormatDateTime(iat);
    }


    /**
     * 获取格式化后的过期时间
     * @return YY-MM-dd hh:mm:ss
     */
    @JsonIgnore
    private String getFormatExp(){
        return DateTimeUtils.getFormatDateTime(exp);
    }
}
