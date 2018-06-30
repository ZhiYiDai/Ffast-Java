package cn.ffast.core.interceptor;


import cn.ffast.core.annotations.Logined;
import cn.ffast.core.annotations.Permission;
import cn.ffast.core.auth.OperatorUtils;
import cn.ffast.core.constant.ResultCode;
import cn.ffast.core.auth.OperatorBase;
import cn.ffast.core.vo.ServiceResult;
import com.baomidou.mybatisplus.toolkit.ReflectionKit;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * 登录、权限拦截器基类
 * Created by daizhiyi on 2017/4/29.
 */
public abstract class BaseAuthInterceptor<T extends OperatorBase> extends HandlerInterceptorAdapter {
    private static Logger logger = LoggerFactory.getLogger(BaseAuthInterceptor.class);

    @Resource
    private OperatorUtils operatorUtils;

    /**
     * 拦截登录&权限
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        logger.debug("###############进入拦截器###############");
        String path = (request.getRequestURI().replace(request.getContextPath(), ""));
        StringBuilder permissionName = new StringBuilder();
        boolean loginVerify = false;

        HandlerMethod hMethod = null;
        String beanName = null;
        if (handler instanceof HandlerMethod) {
            hMethod = ((HandlerMethod) handler);
            Object bean = hMethod.getBean();
            Logined loginedClassAnnotation = bean.getClass().getAnnotation(Logined.class);
            Logined loginedMethodAnnotation = hMethod.getMethodAnnotation(Logined.class);
            if (loginedClassAnnotation != null) {
                if (loginedClassAnnotation.notEffectSelf() &&
                        hMethod.getMethod().getDeclaringClass().getName().equals(bean.getClass().getName())) {
                    loginVerify = false;
                } else {
                    loginVerify = true;
                }
            }
            if (loginedMethodAnnotation != null) {
                if (loginedMethodAnnotation.notEffectSelf()) {
                    loginVerify = false;
                } else {
                    loginVerify = true;
                }
            }
            Permission classPermission = bean.getClass().getAnnotation(Permission.class);
            Permission methodPermission = hMethod.getMethodAnnotation(Permission.class);


            if (methodPermission != null) {
                //如果是独立的权限验证
                if (methodPermission.alone()) {
                    permissionName = new StringBuilder(methodPermission.value());
                } else {
                    // 如果方法权限不为空才会和类权限组合
                    if (classPermission != null && StringUtils.isNotEmpty(methodPermission.value())) {
                        permissionName.append(classPermission.value());
                        permissionName.append(":");
                        permissionName.append(methodPermission.value());
                    }
                }
            }
            beanName = bean.getClass().getName();
        }

        if (loginVerify) {
            Class<T> tClass = ReflectionKit.getSuperClassGenricType(this.getClass(), 0);
            T loginUser = operatorUtils.getTokenUser(request, tClass);
            request.setAttribute("loginUser", loginUser);
            if (loginUser == null) {
                //未登录
                notLogin(response);
                logger.error(beanName + " path:" + path + " [未登录]");
                return false;
            } else if (StringUtils.isNotEmpty(permissionName) &&
                    !verifyPerms(loginUser.getHasRoleId(), permissionName.toString())) {
                //没有权限
                notPermission(response);
                logger.error(beanName + " loginId:" + loginUser.getUserId() +
                        " loginName:" + loginUser.getUserName() + " funcId:" + permissionName + " [没有权限]");
                return false;
            }
        }

        return true;

    }

    private boolean verifyPerms(Long roleId, String permissionName) {
        if (roleId == null) {
            return false;
        } else {
            Set<String> permissions = getRolePerms(roleId);
            return permissions.contains(permissionName);
        }
    }

    private boolean verifyPerms(Set<Long> roleIds, String permissionName) {
        if (roleIds != null) {
            for (Long id : roleIds) {
                boolean result = verifyPerms(id, permissionName);
                if (result) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object o, ModelAndView mav)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object o, Exception excptn)
            throws Exception {
    }


    /**
     * 返回未登录
     *
     * @param response
     * @throws Exception
     */
    protected void notLogin(HttpServletResponse response) throws Exception {
        initResponse(response);
        ServiceResult result = new ServiceResult(ResultCode.NOTLOGIN.getCode(),
                ResultCode.NOTLOGIN.getMessage());
        response.getWriter().print(result.toJSON());
    }

    /**
     * 返回无权限
     *
     * @param response
     * @throws Exception
     */
    protected void notPermission(HttpServletResponse response) throws Exception {
        initResponse(response);
        ServiceResult result = new ServiceResult(ResultCode.PERMISSION_DENIED.getCode(),
                ResultCode.PERMISSION_DENIED.getMessage());
        response.getWriter().print(result.toJSON());
    }

    /**
     * 设置返回类型
     *
     * @param response
     * @throws Exception
     */
    protected void initResponse(HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=UTF-8");

    }


    protected abstract Set<String> getRolePerms(Long roleId);

}
