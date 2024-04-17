package com.jt.www.exception;

/**
* 描述：业务操作异常类
* 类名称：BizException.java
* 作者： wangyang
* 版本：1.0
* 修改： 2018/8/3 9:28
* 创建日期： 2018/8/3 9:28
* 版权：江泰保险经纪股份有限公司
*/
public class BizException extends RuntimeException {
    public BizException(String message, Throwable cause) {
        super(message,cause);
    }

    public BizException(String message) {
        super(message);
    }
}
