package com.jt.www.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 * @Date: 2023/3/1 10:37
 */
public class CreateQRCodeUtil {

    /***
     *
     * @param width  定义图片宽度
     * @param height  定义图片高度
     * @param format  定义图片格式
     * @param content  定义二维码内容
     * @return
     */
    public static byte[] createQR(int width,int height,String format,String content) {
       //  width = 300;        //定义图片宽度
       //  height = 300;       //定义图片高度
       if ("".equals(format)) format = "png";      // 默认 定义图片格式

        //定义二维码的参数
        HashMap hashMap = new HashMap();
        hashMap.put(EncodeHintType.CHARACTER_SET, "utf-8");     //设置编码
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);  //设置容错等级，等级越高，容量越小
        hashMap.put(EncodeHintType.MARGIN, 2);          //设置边距
        //生成二维码
        byte[] bytes = null;
        try {
            //生成矩阵
            //  内容    格式          宽       高
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hashMap);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, format, outputStream);       //输出图像
            bytes = outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

}
