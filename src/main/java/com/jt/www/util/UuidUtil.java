package com.jt.www.util;

import java.util.UUID;

/**
 * @Author: tdh
 * @Date : 2018/11/2
 */
public class UuidUtil {
    /**
     * 获取uuid
     *
     * @return
     */
    public static String getUUIDStr() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
