package com.jt.www.exception;

/**
* 描述：业务操作异常类
* 类名称：BizException.java
*/
public class BizException extends RuntimeException {
    public BizException(String message, Throwable cause) {
        super(message,cause);
    }

    public BizException(String message) {
        super(message);
    }
}
