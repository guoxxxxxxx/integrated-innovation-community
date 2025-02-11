/**
 * @Time: 2025/2/11 20:08
 * @Author: guoxun
 * @File: AuthException
 * @Description: 鉴权异常类
 */

package com.iecas.communitycommon.exception;

public class AuthException extends RuntimeException {

    public AuthException(String message) {
        super(message);
    }
}
