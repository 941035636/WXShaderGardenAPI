package com.jt.www.util;

import java.math.BigDecimal;

public class CalculateUtils {

    private static final BigDecimal unitSiz = new BigDecimal(1024);
    private static final String kbstr = "KB";
    private static final String gbstr = "G";
    private static final String mstr = "M";

    /**
     * 计算文件大小
     * @param size 字节大小
     * @return
     */
    public static String caculateSiz(Long size){
        BigDecimal kbSize = new BigDecimal(size).divide(unitSiz);
        BigDecimal mbSize = null;
        if(kbSize.compareTo(unitSiz)>0){
            mbSize = kbSize.divide(unitSiz);
        }else{
            return new StringBuilder().append(kbSize.intValue()).append(kbstr).toString();
        }
        BigDecimal gbSize = null;
        if(mbSize.compareTo(unitSiz)>0){
            gbSize = mbSize.divide(unitSiz);
           return new StringBuilder().append(gbSize.intValue()).append(gbstr).toString();
        }
        return new StringBuilder().append(mbSize.intValue()).append(mstr).toString();
    }
}
