package com.jt.www.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by terminal on 2020/3/12.
 */
public class PinganZnhbSignUtil {

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {// 拼接时，不包括最后一丄1�7&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    public static String encryptRequest(String request, String merchantKey) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer(filterRequest(request));
        Map<String, String> map = splitStringToMap(sb.toString());

        sb.append(merchantKey);
        String signMethod = map.containsKey("signMethod") ? map.get("signMethod") : "SHA-256";
        String charset = map.containsKey("charset") ? map.get("charset") : "UTF-8";
        // 返回结果
        String encryStr = encryptPwd(sb.toString(), signMethod, charset);
        return encryStr;
    }

    /**
     * 将字符串分割然后以键值对存入 LinkHashMap
     *
     * @param s
     * @return
     */
    public static Map<String, String> splitStringToMap(String s) {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        String[] temp = splitRequest(s); // 将字符串拆分成一维数组
        for (int i = 0; i < temp.length; i++) {
            String[] arr = temp[i].split("="); // 继续分割并存到另一个一临时的一维数组当中
            String value;
            if (arr.length == 1) {
                value = "";
            } else if (arr.length > 2) {
                value = temp[i].substring(arr[0].length() + 1);
            } else {
                value = arr[1];
            }
            map.put(arr[0], value);
        }
        return map;
    }

    public static String[] splitRequest(String request) {
        if (request == null || request.length() == 0) {
            return null;
        }
        if (!request.contains("[")) {
            return request.split("&");
        }
        StringBuffer sb = new StringBuffer();
        int i = request.indexOf("=");
        String key = request.substring(0, i);
        sb.append(key).append("=");
        request = request.substring(i + 1, request.length());
        String value = "";
        int j = 0;
        if (request.startsWith("[")) {
            int l = 0;
            int r = 0;
            for (j = 0; j < request.length(); j++) {
                if (request.charAt(j) == '[') {
                    l++;
                }
                if (request.charAt(j) == ']') {
                    r++;
                }
                if (l != 0 && l == r) {
                    break;
                }
            }
            j++;
        } else {
            j = request.indexOf("&");
        }
        value = request.substring(0, j);
        sb.append(value);
        if (j < request.length()) {
            request = request.substring(j + 1, request.length());
        } else {
            request = "";
        }
        String[] reqs = splitRequest(request);
        if (reqs != null) {
            String[] retReqs = Arrays.copyOf(reqs, reqs.length + 1);
            retReqs[retReqs.length - 1] = sb.toString();
            return retReqs;
        } else {
            String[] retReqs = new String[1];
            retReqs[0] = sb.toString();
            return retReqs;
        }
    }

    public static String filterRequest(String request) {
        StringBuffer sb = new StringBuffer();// 存放最终需要加签的字符串
        String[] fields = splitRequest(request);
        List<String> keyList = new LinkedList<String>();// 存放升序的key列表
        Map<String, String> valueMap = new HashMap<String, String>();// 存放value的map
        if (fields != null && fields.length > 0) {
            for (String field : fields) {
                String fieldKey = field.split("=")[0];
                String fieldValue = "";
                if ("signMethod".equals(fieldKey)) {
                    continue;
                }
                if ("signature".equals(fieldKey)) {
                    continue;
                }
                if ("transId".equals(fieldKey)) {// transId不参与加密
                    continue;
                }
                // if ("sameOrderFlag".equals(fieldKey)) {//sameOrderFlag不参与加密
                // continue;
                // }
                if (field.split("=").length == 1) {
                    continue;
                } else if (field.split("=").length > 2) {
                    fieldValue = field.substring(field.split("=")[0].length() + 1);
                    String tempValue = fieldValue.replace("{", "").replace("}", "").replace("[", "").replace("]", "");
                    String[] tempValues = tempValue.split(",");
                    StringBuffer tempBuffer = new StringBuffer();
                    tempBuffer.append("[");
                    for (int i = 0; i < tempValues.length; i++) {
                        if (i != 0) {
                            tempBuffer.append(",");
                        }
                        tempBuffer.append("{");
                        tempBuffer.append(filterRequest(tempValues[i]));
                        tempBuffer.append("}");
                    }
                    tempBuffer.append("]");
                    fieldValue = tempBuffer.toString();
                } else {
                    fieldValue = field.split("=")[1];
                }
                // 值为空的字段不参与加签
                if (fieldValue == null || "".equals(fieldValue)) {
                    continue;
                }
                valueMap.put(fieldKey, fieldValue);
                keyList.add(fieldKey);
            }
        }
        // 排序
        Collections.sort(keyList);
        // 拼装加签字符串
        for (String key : keyList) {
            if (sb.length() != 0) {
                sb.append("&");
            }
            sb.append(key).append("=").append(valueMap.get(key));
        }
        return sb.toString();
    }

    public static String encryptPwd(String strSrc, String encName, String charset) throws UnsupportedEncodingException {
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes(charset);
        try {
            if (encName == null || encName.equals("")) {
                encName = "SHA-256";
            }
            md = MessageDigest.getInstance(encName);
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

}
