package com.jt.www.util;

import java.text.DecimalFormat;

public class DoubleUtils {
    public static String getTwoDecimal(String num) {
        Double temp = Double.valueOf(num);
        DecimalFormat dFormat = new DecimalFormat("#0.00");
        return dFormat.format(temp);
    }
    public static void main(String[] args) {
        String twoDecimal = getTwoDecimal(12.0+"");
        System.out.println(twoDecimal);
    }
}
