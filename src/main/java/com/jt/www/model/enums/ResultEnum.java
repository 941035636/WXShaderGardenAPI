package com.jt.www.model.enums;


/**
 * 描述：返回的错误码和异常说明
 * 类名称：ResultEnum
 * 作者： wangyang
 * 版本：1.0
 * 修改：
 * 创建日期：
 * 版权：江泰保险经纪股份有限公司
 *
 * @author tdh
 */
public enum ResultEnum implements BaseEnum {
    /**
     * 成功200
     */
    SUCCESS(200, "成功"),
    /**
     * 请求参数错误401
     */
    PARAM_ERROR(401, "请求参数错误"),
    /**
     * 未授权的访问403
     */
    URL_ERROR(403, "未授权的访问"),
    /**
     * 资源不存在404
     */
    RESOUCE_ERROR(404, "资源不存在"),
    /**
     * 请求方式错误405
     */
    METHOD_NOT_ALLOWED(405, "请求方式错误"),
    /**
     * 服务器内部错误500
     */
    SERVER_ERROR(500, "服务器内部错误");


    private int code;

    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }


    @Override
    public String getMsg() {
        return msg;
    }

}
