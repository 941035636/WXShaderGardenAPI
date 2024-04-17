package com.jt.www.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 
 * @author: wephone
 * @Date: 2018/7/19 09:45
 * @ModifiedDate：
 * @Copyright:江泰保险股份有限公司 
 */
public class ValidatorUitl {

    /**
     * 验证手机号是否合法
     *
     * @param mobile
     * @return true/false
     */
    public static boolean isMobileNO(String mobile) {
        String pat = "^1[3456789]\\d{9}$";
        Pattern pattern = Pattern.compile(pat);
        Matcher match = pattern.matcher(mobile);
        boolean isMatch = match.matches();
        return isMatch;
    }

    public static Boolean isIDCardNo(String idCard) {
        if (idCard.length() != 18 && idCard.length() != 15) {
            return false;
        } else {

            /**
             * 十八位身份证号码的正则  ^[1-9][0-9]{5}([1][9][0-9]{2}|[2][0][0|1][0-9])([0][1-9]|[1][0|1|2])([0][1-9]|[1|2][0-9]|[3][0|1])[0-9]{3}([0-9]|[X])$
             */
            String pat1 = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
            /**
             * 十五位身份证号码的正则
             */
            String pat2 = "^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$";

            Pattern pattern1 = Pattern.compile(pat1);
            Matcher match1 = pattern1.matcher(idCard);
            boolean isMatch1 = match1.matches();
            if (isMatch1) {
                return true;
            }

            Pattern pattern2 = Pattern.compile(pat2);
            Matcher match2 = pattern2.matcher(idCard);
            boolean isMatch2 = match2.matches();
            if (isMatch2) {
                return true;
            }
        }
        return false;
    }
}