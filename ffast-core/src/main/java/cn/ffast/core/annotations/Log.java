package cn.ffast.core.annotations;

import java.lang.annotation.*;

/**
 * @description: 操作日志注解
 * @copyright:
 * @createTime: 2017/11/14 14:12
 * @author：dzy
 * @version：1.0
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 操作描述
     */
    String value() default "";

}
