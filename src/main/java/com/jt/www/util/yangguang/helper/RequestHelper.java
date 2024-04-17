package com.jt.www.util.yangguang.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jt.www.util.yangguang.model.RequestBase;
import com.jt.www.util.yangguang.model.ResponseBase;
import com.jt.www.util.yangguang.util.SecurityUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author yinshaobo at 2021/5/19 10:12
 * 公共请求参数构建
 */
public class RequestHelper {

    /**
     * 渠道接入方自己生成的私钥
     */
    private static final String PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDO+f6UGyCXkEAPlXBWoX8sZOgukhWZRXOni6SiDMzlM3ePcawMCwen4kRC+78qbIC1xk2soORFwQztNa+FRYNjMr5v3URdb6lfdJf2qFm5fRDKPIaoxeCKLHbct0iLa5ycMPbL1jRwuKD590XPaDU93S2Qp6g7Q8lojVtfqTqbay8pfbricfkjAI+q95dWRo0/zS5nBvD58/pPiekFIPGPaDHMfAy7i2ZIWpLG4pjU3EtgrFvJl73PQuPlch3B80PZLfP9rkuuWWdOxBbjXaVP2RfF2PJab/zh1IqHpAffWjbXGzA4ggVJQRIlCFJGrwUBlYxHnJWMtcr6o9kr26RPAgMBAAECggEAcCV40VsZf8YDT+5S3j3HIJDUVVeGKHQ/REsfu0ZgRthqMekPpb7cORUak4jAfgYof1WhhKXwyfLeRDFQl0+g70Dxer9TaaU1CqmsJVP2udCQJmRBOnVtQrDw/oq43gJC60aEktHPvDvtzUM0Kjt2jbyoEbcmKmaCXQoqtlyFiuvBOYqojSiivFM54942anq/0jdkNn6DR4J5McYObowoav92Kh0da3gkhq1JGHSSDgBoPhjelv6Bxfwh1fh90bI2a+1BGP6C8kplAFFh2AHVl1MFplCO+9GWGv69ruiDSVZk+ZnMXjXbXAFKeamuQkXOEtKKd2+oCpjABD6vTesZcQKBgQDpy6xxNfwel2pH7E3L/lcYX+692FMm/oG44eHThZyK/CDuiUMG3vw9AreZ7HJw6Tr/TYsvb6vk2h9kTZ0IsdVWeRXG7yheWuX1QVn3FVoDIKGtpvSNuUg68De7d+u4RawoyHKuRmRFSokGhthh52b6yOpghdJz81mamofq3hxCswKBgQDiokNLQtJzLBuxXdtc6/2DK6/ZugKaHNsO8o6a5LsKLGOWL7DfY4xSIMjLGvaTYI+AxUa2yLR16d01qzkMDNCZocItRW53996b1P4HHDyak9pdd04Dcj92TOAYT03UQFhoLDmWJV9MqaF9ziu6tPv+kRQKpv/JQVgTRZL1aFx19QKBgQCPWvTUwi5ir6NakJmvYLgU2RXx0IhEG8B9Ffw7j+zKCAlFWmL//pCZ9GWR+zq84zXqv6h9oLK888ZV4YVNhIV0rXBd990/5eqlNLouWChGTKb2bK39jMOuaAc0azbdWdNTen1fQQhQuIBGwT5C4fnyAmo8XzIJKQkjVGfM6v5XIwKBgQC6QAWzvTe8pPwhOHQFmn+eI/IBqcdnpd01HIurqdw40SAKlijyfRodiInbuM35cjqc5gNmRe1glhBQgeWBDKsGBxTQJaZSe2b7hAA8Ea2Qj3++nkSDuomDDEyCTfI1Q6gj/GxbA12dYC6yeBQh/Yi2vb3Nv06os8XRGLVcbybFXQKBgQCbWCZa9jXGci+b7T2HfvTOvsBbF2lKfuyu1SkBwQJ5ZGRElBtyObw5xE6KSaZboK1c8HDdLt49n+AF9NCTlbdH0Lx44oKXKo9ui1Cl6oqYQsCk+4RHq4TkWJZUhUSusk88n683Qkf+u9Krd8gXxwZ30BcDi413+Pe0KirX35OTzA==";


    /**
     * 请求参数加密加签
     *
     * @param requestBase 请求公共参数对象
     * @param publicKey   渠道管理方颁发的公钥
     */
    public static void encryptAndSign(RequestBase requestBase, String publicKey) throws Exception {
        //业务数据加密
        String bizContent = (String) requestBase.getBizContent();
        String encryptBizContent = SecurityUtils.rsaEncrypt(bizContent, publicKey);
        requestBase.setBizContent(encryptBizContent);
        //数据加签
        //要加签的字符串
        String toSignStr = getSortedStr(requestBase);
        //获得签文 TODO 如果渠道接入方未生成密钥对，则调用 SecurityUtils.md5Sign() 方法进行MD5加签
        String signStr = SecurityUtils.rsaSign(toSignStr, PRIVATE_KEY);
        requestBase.setSignValue(signStr);
    }

    /**
     * 响应数据验签解密
     * @param responseBase 响应数据对象
     * @param publicKey 渠道管理方颁发的公钥
     * @throws Exception e
     */
    public static void checkSignAndDecrypt(ResponseBase responseBase, String publicKey) throws Exception {
        //要验签的字符串
        ResponseBase base = JSON.parseObject(JSON.toJSONString(responseBase), ResponseBase.class);
        base.setSignValue(null);
        String toCheckSignStr = getSortedStr(base);
        //签文
        String signStr = responseBase.getSignValue();
        //验签 TODO 如果渠道接入方未生成密钥对，则调用 SecurityUtils.md5CheckSign() 方法进行MD5验签
        boolean checkSign = SecurityUtils.rsaCheckSign(toCheckSignStr, signStr, publicKey);
        if (!checkSign) {
            throw new RuntimeException("验签失败！");
        }
        //解密
        String decrypt = SecurityUtils.rsaDecrypt((String) responseBase.getData(), PRIVATE_KEY);
        responseBase.setData(decrypt);
    }

    /**
     * 对属性字典排序输出key:value形式的json
     *
     * @param obj 要排序的对象
     * @return json串
     * @throws Exception e
     */
    private static String getSortedStr(Object obj) throws Exception {
        Map<String, Object> map = JSON.parseObject(JSON.toJSONString(obj), new TypeReference<Map<String, Object>>() {
        });
        TreeMap<String, Object> treeMap = new TreeMap<>(map);
        return JSON.toJSONString(treeMap);
    }
}
