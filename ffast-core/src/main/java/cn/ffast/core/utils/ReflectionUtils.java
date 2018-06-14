package cn.ffast.core.utils;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {

    public static void reflectClassValueToNull(Object model, String[] fieldNames) throws Exception {
        if (ArrayUtils.isEmpty(fieldNames)) {
            return;
        }
        List<String> fieldArray = Arrays.asList(fieldNames);
        // 获取此类的所有父类
        List<Class<?>> listSuperClass = new ArrayList<>(10);
        Class<?> superClass = model.getClass().getSuperclass();
        while (superClass != null) {
            if (superClass.getName().equals("java.lang.Object")) {
                break;
            }
            listSuperClass.add(superClass);
            superClass = superClass.getSuperclass();
        }
        // 遍历处理所有父类的字段
        for (Class<?> clazz : listSuperClass) {
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                String name = fields[i].getName();
                if (fieldArray.contains(name)) {
                    Class type = fields[i].getType();
                    Method method = clazz.getMethod("set" + name.replaceFirst(name.substring(0, 1),
                            name.substring(0, 1).toUpperCase()), type);
                    method.invoke(model, new Object[]{null});
                }

            }
        }
        // 处理此类自己的字段
        Field[] fields = model.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String name = fields[i].getName();
            if (fieldArray.contains(name)) {
                Class type = fields[i].getType();
                // 获取属性的set方法
                Method method = model.getClass().getMethod("set" + name.replaceFirst(name.substring(0, 1),
                        name.substring(0, 1).toUpperCase()), type);
                // 将值设为null
                method.invoke(model, new Object[]{null});
            }
        }
    }
}
