package com.jt.www.util;

import com.jt.www.exception.BizException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Map;

@Getter
@Setter
@Component
@Slf4j
public class WordUtils {



    private static Configuration configuration = null;
    /**
     * classpath路径
     */
    private String classpath = getClass().getResource("/").getPath();
    /**
     * 指定FreeMarker模板文件的位置
     */
    private String templatePath = "/pdf";
    /**
     * freeMarker模板文件名称
     */
    private String templateFileName = "pdf.ftl";
    /**
     * 图片路径 —— 默认是classpath下面的images文件夹
     */
    private String imagePath = "/images/";
    /**
     * 字体资源文件 存放路径
     */
    private String fontPath = "pdf/font/";
    /**
     * 字体   [宋体][simsun.ttc]   [黑体][simhei.ttf]
     */
    private String font = "simsun.ttc";
    /**
     * 指定编码
     */
    private String encoding = "UTF-8";



    public WordUtils(){}

    public void createWord(OutputStream outputStream, Map<String, Object> dataMap, String name) {
        //FTL文件所存在的位置
        configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(WordUtils.class, "/template");

        Template t = null;
        try {
            //文件名
            t = configuration.getTemplate(name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //导出文档的存放位置
        File outFile = new File("/template");

        try {
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
            //输出数据到模板
            if (t != null) {
                log.debug("=============================开始生成word报告==============================");
                t.process(dataMap, out);
            }
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }

        try (InputStream inputStream = new FileInputStream(outFile)) {
            // 缓冲区
            byte[] buffer = new byte[1024];
            int bytesToRead;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesToRead);
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        outFile.delete();

    }
    /**
     * 生成word文件
     *
     * @param dataMap      word中需要展示的动态数据，用map集合来保存
     * @param templateName word模板名称，例如：aggregation.ftl
     */
    @SuppressWarnings("unchecked")
    public static String createWord(Map dataMap,String templateName) {
        Writer out = new StringWriter();
        try {
            //创建配置实例
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);

            //设置编码
            configuration.setDefaultEncoding("UTF-8");
            //ftl模板文件
            configuration.setClassForTemplateLoading(WordUtils.class, "/template");

            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            configuration.setLogTemplateExceptions(false);
            configuration.setWrapUncheckedExceptions(true);

            //获取模板
            Template template = configuration.getTemplate(templateName);
            //将合并后的数据和模板写入到流中，这里使用的字符流
            template.process(dataMap,out);
            out.flush();
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("word生成失败！");
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                throw new BizException("关闭流失败！");
            }
        }

    }

}
