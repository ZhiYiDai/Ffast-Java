package cn.ffast.core.support;

import cn.ffast.core.annotations.CrudConfig;
import cn.ffast.core.annotations.Permission;
import cn.ffast.core.auth.OperatorBase;
import cn.ffast.core.utils.AnnotationUtils;
import cn.ffast.core.vo.ServiceResult;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.web.method.HandlerMethod;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @description: 基础增删改查控制器
 * @copyright:
 * @createTime: 2017/9/5 15:17
 * @author：dzy
 * @version：1.0
 */

public abstract class BaseCrudController<T extends BaseEntity, S extends ICrudService<T, ID>, ID> extends BaseController {

    /**
     * 初始化Controller
     */
    @PostConstruct
    private void initCrudController() {
        CrudConfig crudConfig = getCrudConfig();
        /**
         * 增删改查权限名自定义
         */
        if (crudConfig != null) {
            Method[] methods = getClass().getSuperclass().getMethods();
            for (int i = 0; i < methods.length; i++) {
                switch (methods[i].getName()) {
                    case "create":
                        if (!"create".equals(crudConfig.retrievePermission())) {
                            AnnotationUtils.setAnnotationValue(methods[i].getAnnotation(Permission.class), "value", crudConfig.createPermission());
                        }
                        break;
                    case "list":
                        if (!"list".equals(crudConfig.retrievePermission())) {
                            AnnotationUtils.setAnnotationValue(methods[i].getAnnotation(Permission.class), "value", crudConfig.retrievePermission());
                        }
                        break;
                    case "update":
                        if (!"update".equals(crudConfig.updatePermission())) {
                            AnnotationUtils.setAnnotationValue(methods[i].getAnnotation(Permission.class), "value", crudConfig.updatePermission());
                        }
                        break;
                    case "delete":
                        if (!"delete".equals(crudConfig.deletePermission())) {
                            AnnotationUtils.setAnnotationValue(methods[i].getAnnotation(Permission.class), "value", crudConfig.deletePermission());
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }


    /**
     * 查询
     *
     * @param m
     * @param page
     * @param simple 只查少数字段
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    @Permission(value = "list")
    public ServiceResult list(T m, Integer pageSize, Integer page, Boolean simple, String sortField, String sortOrder) {
        ServiceResult beforeResult = listBefore(m);
        if (beforeResult != null) {
            return beforeResult;
        }
        // 查询字段
        String[] properties = ((simple != null && simple) ? getSimpleProperties() : getProperties());
        Page<T> pageM = new Page<T>();
        if (pageSize == null || page == null) {
            pageSize = 1000;
            page = 0;
        }
        // 排序相关
        if (sortOrder != null) {
            pageM.setAsc("asc".equals(sortOrder));
        } else {
            pageM.setAsc(isAsc());
        }
        if (sortField == null) {
            sortField = getSortField();
        }
        pageM.setOrderByField(sortField);
        pageM.setCurrent(page);
        pageM.setSize(pageSize);
        return getService().findListByPage(m, pageM);
    }

    /**
     * 增加
     *
     * @param m
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @Permission(value = "create")
    public ServiceResult create(@Valid T m, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ServiceResult(false).setMessage(bindingResult.getFieldError().getDefaultMessage());
        }
        m.setCreatorId(getLoginUserId());
        ServiceResult beforeResult = createBefore(m);
        if (beforeResult != null) {
            return beforeResult;
        }
        ServiceResult result = getService().create(m);
        createAfter(m, result);
        return result;
    }

    /**
     * 更新
     *
     * @param m
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Permission(value = "update")
    public ServiceResult update(@Valid T m, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ServiceResult(false).setMessage(bindingResult.getFieldError().getDefaultMessage());
        }
        m.setLastModifierId(getLoginUserId());
        ServiceResult beforeResult = updateBefore(m);
        if (beforeResult != null) {
            return beforeResult;
        }
        CrudConfig crudConfig = getCrudConfig();
        ServiceResult result = getService().update(m, isUpdateAllColumn(crudConfig), getUpdateIgnoreProperties(crudConfig));
        updateAfter(m, result);
        return result;
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @Permission(value = "delete")
    public ServiceResult delete(String ids) {
        ServiceResult beforeResult = deleteBefore(ids);
        if (beforeResult != null) {
            return beforeResult;
        }
        ServiceResult result = getService().delete(ids);
        deleteAfter(ids, result);
        return result;
    }

    /**
     * 获得服务层 供子类覆盖
     *
     * @return
     */
    protected abstract S getService();

    @Override
    protected abstract Logger getLogger();

    private String[] getProperties() {
        CrudConfig crudConfig = getCrudConfig();
        if (crudConfig != null && crudConfig.properties().length > 0) {
            return crudConfig.properties();
        } else {
            return null;
        }
    }

    private String[] getSimpleProperties() {
        CrudConfig crudConfig = getCrudConfig();
        if (crudConfig != null && crudConfig.properties().length > 0) {
            return crudConfig.properties();
        } else {
            return null;
        }
    }


    /**
     * 以下方法可以再子类覆盖还实现自定义操作
     */

    /**
     * 创建前
     *
     * @param m
     * @return
     */
    protected ServiceResult createBefore(T m) {
        return null;
    }

    /**
     * 删除前
     *
     * @param ids
     * @return
     */
    protected ServiceResult deleteBefore(String ids) {
        return null;
    }

    /**
     * 更新前
     *
     * @param m
     * @return
     */
    protected ServiceResult updateBefore(T m) {
        return null;
    }

    /**
     * 查询前
     *
     * @param m
     * @return
     */
    protected ServiceResult listBefore(T m) {
        return null;
    }

    /**
     * 数据库数据插入后
     *
     * @param m
     * @param result
     */
    protected void createAfter(T m, ServiceResult result) {
    }

    /**
     * 数据库数据删除后
     *
     * @param ids
     * @param result
     */
    protected void deleteAfter(String ids, ServiceResult result) {
    }

    /**
     * 数据库数据更新后
     *
     * @param m
     * @param result
     */
    protected void updateAfter(T m, ServiceResult result) {
    }

    /**
     * 数据库数据查询后
     *
     * @param m
     * @param result
     */
    protected void listAfter(T m, ServiceResult result) {
    }



    final static String[] ignore = {"create_time", "creator_id"};
    /**
     * 从注解配置获得是否更新所有字段
     *
     * @return
     */
    private String[] getUpdateIgnoreProperties(CrudConfig crudConfig) {
        if (crudConfig != null) {
            return ArrayUtils.addAll(ignore, crudConfig.updateIgnoreProperties());
        } else {
            return ignore;
        }
    }

    /**
     * 从注解配置获得是否更新所有字段
     *
     * @return
     */
    private boolean isUpdateAllColumn(CrudConfig crudConfig) {
        crudConfig = getCrudConfig();
        if (crudConfig != null && crudConfig.updateAllColumn()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 默认排序字段
     *
     * @return
     */
    private String getSortField() {
        CrudConfig crudConfig = getCrudConfig();
        if (crudConfig != null) {
            return crudConfig.sortField();
        }
        return null;
    }

    /**
     * 默认是否升序
     *
     * @return
     */
    private boolean isAsc() {
        CrudConfig crudConfig = getCrudConfig();
        if (crudConfig != null && crudConfig.isAsc()) {
            return true;
        }
        return false;
    }


    private CrudConfig getCrudConfig() {
        return getClass().getAnnotation(CrudConfig.class);
    }
}
