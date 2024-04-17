package com.jt.www.exception;

/**
* 描述：程序运行中产生的异常，用
* 这个异常类进行重新封装返回给客户端
*/
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }
}
