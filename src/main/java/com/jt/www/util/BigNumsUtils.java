package com.jt.www.util;

import java.math.BigDecimal;

/**
 * 高精度计算工具类
 *
 * @author create by fx
 */
public class BigNumsUtils {

    /**
     * 精确的除法运算。除不尽时，由scale参数指 定精度 四舍五入。string
     *
     * @param v1
     *            被除数
     * @param v2
     *            除数
     * @param scale
     *            表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static String strDiv(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();
    }


    /**
     * 提供精确的乘法运算。 保留scale 位小数 String
     *
     * @param v1
     *            被乘数
     * @param v2
     *            乘数
     * @return 两个参数的积
     */
    public static String strMul2(String v1, String v2,int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

}
