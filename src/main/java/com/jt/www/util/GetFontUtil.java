package com.jt.www.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;

/**
 * @Author: sunyuan
 * @Date: 2020/4/16 21:00
 * @Version 1.0
 */
@Slf4j
public class GetFontUtil {

    public static void printFontName(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontFamilies = ge.getAvailableFontFamilyNames();
        for (String s : fontFamilies) {
            System.out.println(s);
        }
    }

    public static Font getFont(int style, Float size) {
        try {
            ClassPathResource classPathResource = new ClassPathResource("/template/simhei.ttf");
            File file = classPathResource.getFile();
            log.info("字体信息:{}",file);
            FileInputStream fileInputStream = new FileInputStream(file);
            Font font = Font.createFont(Font.TRUETYPE_FONT, fileInputStream);
            font = font.deriveFont(style,size);
            fileInputStream.close();
            return font;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
