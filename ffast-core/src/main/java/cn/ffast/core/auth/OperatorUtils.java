package cn.ffast.core.auth;

import cn.ffast.core.redis.RedisCacheUtils;
import cn.ffast.core.utils.Md5Utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
public class OperatorUtils {
    private final static String USER_SESSION_KEY = "UserSession:";
    private final static String HEADER_KEY = "Authorization";
    private final static String ATTRIBUTE_KEY = "loginUser";

    @Resource
    private RedisCacheUtils redisCacheUtils;

    @Value("${auth.type}")
    private int type;
    @Value("${auth.expirationSecond}")
    private int expirationSecond;
    @Value("${auth.jwt.secret}")
    private String jwtSecret;

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    /**
     * 获取后台管理登录信息
     *
     * @return
     */
    public <T extends OperatorBase> T getTokenUser(Class<T> clazz) {
        return getTokenUser(getRequest(), clazz);
    }

    /**
     * 获取后台管理登录信息
     *
     * @return
     */
    public OperatorBase getTokenUser() {
        return getTokenUser(getRequest(), OperatorBase.class);
    }

    /**
     * 获取后台管理登录信息
     *
     * @return
     */
    public <T extends OperatorBase> T getUser(Class<T> clazz) {
        Object obj = getRequest().getAttribute(ATTRIBUTE_KEY);
        if (obj == null) {
            obj = getTokenUser(clazz);
        }
        return obj == null ? null : (T) obj;
    }

    /**
     * 获取后台管理登录信息
     *
     * @return
     */
    public OperatorBase getTokenUser(HttpServletRequest request) {
        return getTokenUser(request, OperatorBase.class);
    }

    /**
     * 获取后台管理登录信息
     *
     * @return
     */
    public <T extends OperatorBase> T getTokenUser(HttpServletRequest request, Class<T> clazz) {
        String token = request.getHeader(HEADER_KEY);
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }
        if (StringUtils.isNotEmpty(token)) {
            T obj = null;
            switch (type) {
                case 1://redis转对象
                    obj = redisCacheUtils.getCacheObject(USER_SESSION_KEY + token, clazz);
                    break;
                default://jwt转对象
                    obj = JwtUtils.unsign(token, clazz, jwtSecret);
            }
            return obj;
        } else {
            return null;
        }
    }

    /**
     * 设置后台管理登录信息
     *
     * @return
     */
    public <T extends OperatorBase> String createToken(T loginUser) {
        return createToken(loginUser, expirationSecond);
    }



    /**
     * 设置后台管理登录信息
     *
     * @return
     */
    public <T extends OperatorBase> String createToken(T loginUser, long seconds) {
        String token = null;
        switch (type) {
            case 1://放redis
                token = Md5Utils.hash(Md5Utils.getUUID() + loginUser.getUserId());
                loginUser.setToken(token);
                redisCacheUtils.setCacheObject(USER_SESSION_KEY + loginUser.getToken(), loginUser, seconds);
                break;
            default://放jwt
                token = JwtUtils.sign(loginUser, seconds, jwtSecret);
        }
        return token;
    }

    /**
     * 移除登录用户
     *
     * @param token
     */
    public void removeLoginUser(String token) {
        redisCacheUtils.delete(USER_SESSION_KEY + token);
    }

    /**
     * 获取后台管理登录用户id
     *
     * @return
     */
    public Long getLoginUserId() {
        OperatorBase operator = getUser(OperatorBase.class);
        if (operator != null) {
            return operator.getUserId();
        }
        return null;
    }
}
