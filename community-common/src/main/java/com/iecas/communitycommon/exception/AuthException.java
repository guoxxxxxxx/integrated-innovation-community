/**
 * @Time: 2025/2/11 20:08
 * @Author: guoxun
 * @File: AuthException
 * @Description: 鉴权异常类
 */

package com.iecas.communitycommon.exception;

import com.iecas.communitycommon.constant.HttpStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthException extends RuntimeException {

    private int statusCode;

    public AuthException(String message) {
        super(message);
        this.statusCode = HttpStatusEnum.AUTH_NO_AUTHORITY.getStatusCode();
    }

    public AuthException(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }
}
