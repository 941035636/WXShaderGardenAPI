package com.jt.www.util.yangguang.model;

/**
 * @author yinshaobo at 2021/5/19 10:14
 * 公共请求参数对象
 */
public class RequestBase {

    /**
     * 应用授权key
     */
    private String appKey;

    /**
     * 请求业务数据
     */
    private Object bizContent;

    /**
     * 签名
     */
    private String signValue;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 版本号
     */
    private String version;

    /**
     * 加密类型
     */
    private String encryptType;

    /**
     * 服务名
     */
    private String serviceName;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Object getBizContent() {
        return bizContent;
    }

    public void setBizContent(Object bizContent) {
        this.bizContent = bizContent;
    }

    public String getSignValue() {
        return signValue;
    }

    public void setSignValue(String signValue) {
        this.signValue = signValue;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEncryptType() {
        return encryptType;
    }

    public void setEncryptType(String encryptType) {
        this.encryptType = encryptType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
