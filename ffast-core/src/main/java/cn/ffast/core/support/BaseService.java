package cn.ffast.core.support;

import cn.ffast.core.auth.OperatorBase;
import cn.ffast.core.utils.HttpServletUtils;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.ReflectionKit;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @copyright:
 * @createTime: 2017/10/28 23:20
 * @author：dzy
 * @version：1.0
 */
public class BaseService<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> {
    protected HttpServletRequest getRequest() {
        return HttpServletUtils.getRequest();
    }

    protected HttpServletResponse getResponse() {
        return HttpServletUtils.getResponse();
    }


    /**
     * 获取所有请求参数
     *
     * @return 返回所有请求参数
     */
    protected Map<String, String> getAllParam() {
        return HttpServletUtils.getAllParam();
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
        Object obj = HttpServletUtils.getRequestAttribute("loginUser");
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

    @Override
    protected Class<T> currentModelClass() {
        return ReflectionKit.getSuperClassGenricType(this.getClass(), 1);
    }
}
