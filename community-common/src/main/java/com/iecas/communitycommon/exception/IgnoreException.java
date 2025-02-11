package com.iecas.communitycommon.exception;


/**
 * 忽略异常，不重要的异常
 */
public class IgnoreException extends RuntimeException {
    public IgnoreException(String message) {
        super(message);
    }
}
