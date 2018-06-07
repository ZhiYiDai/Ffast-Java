package cn.ffast.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

public class AnnotationUtils {

    public static void setAnnotationValue(Annotation annotationClass, String key, Object value) {
        if(annotationClass==null){}
        try {
            //获取这个代理实例所持有的 InvocationHandler
            InvocationHandler h = Proxy.getInvocationHandler(annotationClass);
            // 获取 AnnotationInvocationHandler 的 memberValues 字段
            Field hField = null;
            hField = h.getClass().getDeclaredField("memberValues");
            // 因为这个字段事 private final 修饰，所以要打开权限
            hField.setAccessible(true);
            Map memberValues = null;
            memberValues = (Map) hField.get(h);
            // 修改 权限注解value 属性值
            memberValues.put(key, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
