package com.jt.www.util;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URL;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.IoUtils;
import lombok.extern.slf4j.Slf4j;


/**
 * @Author: Sy
 * @Date: 2022/4/29 17:04
 * @Version 1.0
 */
@Slf4j
public class MyStringImageConverter implements Converter<String> {
    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.IMAGE;
    }

    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                    GlobalConfiguration globalConfiguration) {
        throw new UnsupportedOperationException("Cannot convert images to string");
    }

    // 图片失效处理
    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws IOException {
     //   Long startTime = System.currentTimeMillis();
        InputStream inputStream = null;
       // BufferedInputStream bis = null;
        try {
            if (value == null || "".equals(value)) {
                return new CellData("");
            }
            URL urlValue = new URL(value);
          if(urlValue.openStream()!=null) {
               inputStream = urlValue.openStream();
            }else {
              return new CellData("无法加载图片");
           }
          byte[] bytes = IoUtils.toByteArray(inputStream);
//            Long endTime = System.currentTimeMillis();
//            log.info("url:{}",(endTime - startTime));
            return new CellData(bytes);
        } catch (ConnectException exception) {
            return new CellData("无法加载图片");
        } catch (FileNotFoundException fileNotFoundException) {
            return new CellData("无法加载图片");
        } finally {
            if (inputStream != null) inputStream.close();
            //if (bis != null) bis.close();

        }

    }

}
