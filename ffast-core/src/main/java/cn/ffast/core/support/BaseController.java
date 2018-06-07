/*
 * Copyright (C)
 * Created by dzy on 2017/4/14.
 */
package cn.ffast.core.support;



import cn.ffast.core.auth.OperatorBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public abstract class BaseController {
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    protected HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    }

    protected void setAttribute(String key, Object data){
        getHttpSession().setAttribute(key,data);
    }

    protected HttpSession getHttpSession(){
        return getRequest().getSession();
    }

    protected Object getAttribute(String var1){
        return getHttpSession().getAttribute(var1);
    }



    /**
     * 获取所有请求参数
     *
     * @return 返回所有请求参数
     */
    protected Map<String, String> getAllParam() {
        Map params = new HashMap<>();
        HttpServletRequest request = getRequest();
        Enumeration enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            String val = request.getParameter(paraName);
            params.put(paraName, val);
        }
        return params;
    }

    /**
     * 获取请求参数
     *
     * @return 返回所有请求参数
     */
    protected Object getRequestParam(String key) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            return request.getParameter(key);
        }
        return null;
    }

    /**
     * 获得请求参数返回指定的强制转换对象
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    protected <T> T getRequestParam(String key, Class<T> clazz) {
        Object obj = getRequestParam(key);
        if (obj != null) {
            return (T) obj;
        }
        return null;
    }

    /**
     * 获得请求参数返回字符串
     * @param key
     * @return
     */
    protected String getRequestParamString(String key) {
        Object obj = getRequestParam(key);
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    /**
     * 获取后台管理登录信息
     *
     * @return
     */
    protected OperatorBase getLoginUser() {
        Object obj = getAttribute("loginUser");
        if (obj != null) {
            return (OperatorBase) (obj);
        }
        return null;
    }

    /**
     * 获取后台管理登录用户id
     *
     * @return
     */
    protected Long getLoginUserId() {
        OperatorBase operator = getLoginUser();
        if (operator != null) {
            return operator.getUserId();
        }
        return null;
    }


    /**
     * 获取后台管理登录信息
     * @return
     */
    protected  <T> T getLoginUser(Class<T> clazz) {
        return (T) (getAttribute("loginUser"));
    }

    /**
     * 获得日志对象，供子类重写
     * @return
     */
    protected abstract Logger getLogger();

}
