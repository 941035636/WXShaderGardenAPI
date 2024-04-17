package com.jt.www.exception;

/**
* 描述：参数校验异常
 *
* 类名称：ParamException.java
* 作者： wangyang
* 版本：1.0
* 修改： 2018/8/3 18:54
* 创建日期： 2018/8/3 18:54
* 版权：江泰保险经纪股份有限公司
*/
public class ParamException extends RuntimeException  {
    public ParamException(String message, Throwable cause) {
        super(message,cause);
    }
    public ParamException(String message) {
        super(message);
    }
}
