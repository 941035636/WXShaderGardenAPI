package com.jt.www.exception;

/**
* 描述：参数校验异常
 *
* 类名称：ParamException.java
*/
public class ParamException extends RuntimeException  {
    public ParamException(String message, Throwable cause) {
        super(message,cause);
    }
    public ParamException(String message) {
        super(message);
    }
}
