package com.jt.www.config.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName JsonObjectSerializer
 * @Description json 序列化配置
 * @Author yanglichao
 * @Date 2019/5/3
 * @Version 1.0
 **/
@Component
public class JsonObjectSerializer extends ObjectMapper {

    private static final long serialVersionUID = 3223645203459453114L;

    /**
     * 构造函数
     */
    public JsonObjectSerializer() {
        super();
        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        registerModule(simpleModule);
        // 设置日期返回格式
        setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }
}
