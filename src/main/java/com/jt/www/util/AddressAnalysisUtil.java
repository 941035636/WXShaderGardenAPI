package com.jt.www.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Sy
 * @Date: 2022/5/12 16:07
 * @Version 1.0
 */
public class AddressAnalysisUtil {




    public static Map<String,String> addressAnalysis(String address) {
        //1.地址的正则表达式
        String regex = "(?<province>[^省]+省|.+自治区|[^澳门]+澳门|[^香港]+香港|[^市]+市)?(?<city>[^自治州]+自治州|[^特别行政区]+特别行政区|[^市]+市|.*?地区|.*?行政单位|.+盟|市辖区|[^县]+县)(?<county>[^县]+县|[^市]+市|[^镇]+镇|[^区]+区|[^乡]+乡|.+场|.+旗|.+海域|.+岛)?(?<address>.*)";
        //2、创建匹配规则
        Matcher m = Pattern.compile(regex).matcher(address);
        String province;
        String city;
        String county;
        String detailAddress;
        Map<String, String> map = new HashMap<>(16);
        while(m.find()){
            //加入省
            province = m.group("province");
            map.put("province", province == null ? "" : province.trim());
            //加入市
            city = m.group("city");
            map.put("city", city == null ? "" : city.trim());
            //加入区
            county = m.group("county");
            map.put("county", county == null ? "" : county.trim());
            //详细地址
            detailAddress = m.group("address");
            map.put("address", detailAddress == null ? "" : detailAddress.trim());
            if (map.get("city").equals(map.get("province")) ) {
                map.put("city", "市辖区");
            }
        }
        return map;
    }
}


