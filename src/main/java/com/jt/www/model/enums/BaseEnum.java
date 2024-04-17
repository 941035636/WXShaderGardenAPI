package com.jt.www.model.enums;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * 描述：
 * 类名称：公用的枚举类，用来统一定义异常枚举
 * 作者： wangyang
 * 版本：1.0
 * 修改：
 * 创建日期：
 * 版权：江泰保险经纪股份有限公司
 *
 * @author wangyang
 */
public interface BaseEnum {
    /**
     * 获取编码
     *
     * @return
     */
    int getCode();

    /**
     * 获取信息
     *
     * @return
     */
    String getMsg();

    /**
     * 转化为
     *
     * @param name
     * @return
     */
    default String convertToParamEnum(String name) {
        return "hello " + name;
    }

    /**
     * 需要转换到前端展示的属性
     *
     * @return
     */
    default Map<String, Object> toMap() {
        return ImmutableMap.<String, Object>builder().put("code", getCode()).put("msg", getMsg()).build();
    }
}
