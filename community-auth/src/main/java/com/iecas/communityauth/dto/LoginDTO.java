/**
 * @Time: 2025/2/10 14:24
 * @Author: guoxun
 * @File: LoginDTO
 * @Description: 用户登录dto  [密码 | 登录验证码] 二选一, 二者都存在的话密码优先
 */

package com.iecas.communityauth.dto;


import lombok.Data;



@Data
public class LoginDTO {

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 登录验证码
     */
    private String authCode;
}
