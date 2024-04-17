package com.jt.www.exception;

/**
* 描述：程序运行中产生的异常，用
* 这个异常类进行重新封装返回给客户端
* 类名称：ServiceException.java
* 作者： wangyang
* 版本：1.0
* 修改： 2018/8/3 9:29
* 创建日期： 2018/8/3 9:29
* 版权：江泰保险经纪股份有限公司
*/
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }
}
