/**
 * @Time: 2025/2/20 22:00
 * @Author: guoxun
 * @File: AuthGlobalException
 * @Description:
 */

package com.iecas.communityauth.config;


import com.iecas.communitycommon.common.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
@Slf4j
public class AuthGlobalException {


    /**
     * 用户权限异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccountStatusException.class)
    public CommonResult accountStatusException(AccountStatusException e) {
        return new CommonResult().status(5403).message(e.getMessage());
    }
}
