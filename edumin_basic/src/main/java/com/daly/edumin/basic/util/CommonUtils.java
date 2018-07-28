package com.daly.edumin.basic.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by daly on 2018/7/18.
 */
public class CommonUtils {
    /**
     * 获取类的属性名
     * @param clazz
     * @return
     */
    public static List<String> getField(Class<?> clazz) {
        List<String> attrList = new ArrayList<>();
        // 获取实体类的所有属性信息，返回Field数组
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields)
            attrList.add(field.getName());
        return attrList;
    }

    /**
     * 获取类的get方法名
     * @param clazz
     * @return
     */
    public static Map<String,Method> getMethodByClassName(Class<?> clazz) throws IntrospectionException {
        Map<String,Method> methodMap = new HashMap<>();
        // 获取实体类的所有方法信息，返回Method Map
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
            Method getMethod = pd.getReadMethod();//获得get方法
            methodMap.put(getMethod.toString(),getMethod);
        }
        return methodMap;
    }
}
