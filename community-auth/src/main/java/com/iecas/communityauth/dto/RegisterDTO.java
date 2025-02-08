/**
 * @Time: 2025/2/8 13:49
 * @Author: guoxun
 * @File: RegisterDTO
 * @Description:
 */

package com.iecas.communityauth.dto;


import lombok.Data;



@Data
public class RegisterDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String authCode;
}
