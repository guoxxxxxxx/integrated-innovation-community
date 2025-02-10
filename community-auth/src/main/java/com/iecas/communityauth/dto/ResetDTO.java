/**
 * @Time: 2025/2/10 14:15
 * @Author: guoxun
 * @File: ResetDTO
 * @Description:
 */

package com.iecas.communityauth.dto;


import lombok.Data;



@Data
public class ResetDTO {

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 验证码
     */
    private String authCode;

    /**
     * 新密码
     */
    private String password;
}
