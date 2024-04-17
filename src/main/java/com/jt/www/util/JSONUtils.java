package com.jt.www.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

public class JSONUtils {
	
		private static Log log = LogFactory.getLog(JSONUtils.class);
 	    /** 空的 {@code JSON} 数据 - <pre>"{}"</pre>。 */
	    public static final String EMPTY_JSON = "{}";
	  
	    /** 空的 {@code JSON} 数组(集合)数据 - {@code "[]"}。 */
	    public static final String EMPTY_JSON_ARRAY = "[]";
	  
	    /** 默认的 {@code JSON} 日期/时间字段的格式化模式。 */
	    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";
	  
	    /**
	     * <p>
	     * <pre>JSONUtils</pre> instances should NOT be constructed in standard programming. Instead,
	     * the class should be used as <pre>JSONUtils.fromJson("foo");</pre>.
	     * </p>
	     * <p>
	     * This constructor is public to permit tools that require a JavaBean instance to operate.
	     * </p>
	     */
	    public JSONUtils() {
	        super();
	    }
	  
	    /**
	     * 将给定的目标对象根据指定的条件参数转换成 {@code JSON} 格式的字符串。
	     * <p />
	     * <strong>该方法转换发生错误时，不会抛出任何异常。若发生错误时，曾通对象返回 <pre>"{}"</pre>； 集合或数组对象返回 <pre>"[]"</pre>
	     * </strong>
	     *
	     * @param target 目标对象。
	     * @param targetType 目标对象的类型。
	     * @param isSerializeNulls 是否序列化 {@code null} 值字段。
	     * @param version 字段的版本号注解。
	     * @param datePattern 日期字段的格式化模式。
	     * @param excludesFieldsWithoutExpose 是否排除未标注 {@literal @Expose} 注解的字段。
	     * @return 目标对象的 {@code JSON} 格式的字符串。
	     * @since 1.0
	     */
	    public static String toJson(Object target, Type targetType, boolean isSerializeNulls, Double version,
	            String datePattern, boolean excludesFieldsWithoutExpose) {
	        if (target == null) return EMPTY_JSON;
	        GsonBuilder builder = new GsonBuilder();
	        if (isSerializeNulls) builder.serializeNulls();
	        if (version != null) builder.setVersion(version.doubleValue());
	        if (isBlank(datePattern)) datePattern = DEFAULT_DATE_PATTERN;
	        builder.setDateFormat(datePattern);
	        if (excludesFieldsWithoutExpose) builder.excludeFieldsWithoutExposeAnnotation();
	        return toJson(target, targetType, builder);
	    }

	    /**
	     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean} 对象。</strong>
	     * <ul>
	     * <li>该方法不会转换 {@code null} 值字段；</li>
	     * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	     * </ul>
	     *
	     * @param target 要转换成 {@code JSON} 的目标对象。
	     * @param excludesFieldsWithoutExpose 是否排除未标注 {@literal @Expose} 注解的字段。
	     * @return 目标对象的 {@code JSON} 格式的字符串。
	     * @since 1.0
	     */
	    public static String toJson(Object target, boolean excludesFieldsWithoutExpose) {
	        return toJson(target, null, false, null, null, excludesFieldsWithoutExpose);
	    }
	  
	    /**
	     * 将给定的目标对象根据{@code GsonBuilder} 所指定的条件参数转换成 {@code JSON} 格式的字符串。
	     * <p />
	     * 该方法转换发生错误时，不会抛出任何异常。若发生错误时，{@code JavaBean} 对象返回 <pre>"{}"</pre>； 集合或数组对象返回
	     * <pre>"[]"</pre>。 其本基本类型，返回相应的基本值。
	     *
	     * @param target 目标对象。
	     * @param targetType 目标对象的类型。
	     * @param builder 可定制的{@code Gson} 构建器。
	     * @return 目标对象的 {@code JSON} 格式的字符串。
	     * @since 1.1
	     */
	    public static String toJson(Object target, Type targetType, GsonBuilder builder) {
	        if (target == null) return EMPTY_JSON;
	        Gson gson = null;
	        if (builder == null) {
	            gson = new Gson();
	        } else {
	            gson = builder.create();
	        }
	        String result = EMPTY_JSON;
	        try {
	            if (targetType == null) {
	                result = gson.toJson(target);
	            } else {
	                result = gson.toJson(target, targetType);
	            }
	        } catch (Exception ex) {
	        	log.error("目标对象 " + target.getClass().getName() + " 转换 JSON 字符串时，发生异常！" +  ex.getMessage());
	            if (target instanceof Collection<?> || target instanceof Iterator<?> || target instanceof Enumeration<?>
	                    || target.getClass().isArray()) {
	                result = EMPTY_JSON_ARRAY;
	            }
	        }
	        return result;
	    }
	     
	    private static boolean isBlank(String text) {
	        return null == text || "".equals(text.trim());
	    }
}
