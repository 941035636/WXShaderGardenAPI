package com.jt.www.util;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CopyObjectUtil {

    /**
     * @param input 输入集合
     * @param clzz  输出集合类型
     * @param <E>   输入集合类型
     * @param <T>   输出集合类型
     * @return 返回集合
     */
    public static <E, T> List<T> convertList2List(List<E> input, Class<T> clzz) {
        List<T> output = Lists.newArrayList();
        if (input != null && !input.isEmpty()) {
            for (E source : input) {
                T target = BeanUtils.instantiate(clzz);
                BeanUtils.copyProperties(source, target);
                output.add(target);
            }
        }
        return output;
    }


    /**
     * 获取对象中为 null 的属性的名称
     *
     * @param object 对象
     * @return 属性名数组
     */
    public static String[] getNullPropertyNames(Object object) {
        final BeanWrapper src = new BeanWrapperImpl(object);
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor propertyDescriptor : src.getPropertyDescriptors()) {
            String propertyName = propertyDescriptor.getName();
            if (src.getPropertyValue(propertyName) == null) {
                emptyNames.add(propertyName);
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
