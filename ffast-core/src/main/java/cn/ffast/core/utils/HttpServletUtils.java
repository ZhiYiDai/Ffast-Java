package cn.ffast.core.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class HttpServletUtils {

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    }

    public static void setAttribute(String key, Object data) {
        getHttpSession().setAttribute(key, data);
    }

    public static HttpSession getHttpSession() {
        return getRequest().getSession();
    }

    public static Object getSessionAttribute(String var1) {
        return getHttpSession().getAttribute(var1);
    }

    public static Object getRequestAttribute(String var1) {
        return getRequest().getAttribute(var1);
    }


    /**
     * 获取所有请求参数
     *
     * @return 返回所有请求参数
     */
    public static Map<String, String> getAllParam() {
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
    public static Object getRequestParam(String key) {
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
    public static <T> T getRequestParam(String key, Class<T> clazz) {
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
    public static String getRequestParamString(String key) {
        Object obj = getRequestParam(key);
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }
}
