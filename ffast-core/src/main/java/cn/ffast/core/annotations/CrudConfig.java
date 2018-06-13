package cn.ffast.core.annotations;

import java.lang.annotation.*;

/**
 * @description: 基础增删改配置项
 * @copyright:
 * @createTime: 2017/11/14 14:12
 * @author：dzy
 * @version：1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CrudConfig {

    /**
     * 是否更新所有字段
     */
    boolean updateAllColumn() default false;

    /**
     * 查询字段
     */
    String[] properties() default {};

    /**
     * 简单的查询字段
     */
    String[] simpleProperties() default {};

    /**
     * 默认排序字段
     */
    String sortField() default "id";

    /**
     * 默认是否升序
     */
    boolean isAsc() default true;

    /**
     * 增加接口权限名
     */
    String createPermission() default "create";
    /**
     * 查询接口权限名
     */
    String retrievePermission() default "list";
    /**
     * 更新接口权限名
     */
    String updatePermission() default "update";
    /**
     * 删除接口权限名
     */
    String deletePermission() default "delete";
    /**
     * 更新排除字段
     */
    String[] updateIgnoreProperties() default {};

}
