package cn.ffast.core.service;


import cn.ffast.core.redis.RedisCacheUtils;
import cn.ffast.core.utils.CaptchaUtils;
import cn.ffast.core.utils.HttpServletUtils;
import cn.ffast.core.utils.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dzy
 * @Description: 验证码Service实现
 * @Copyright:
 * @Created
 * @vesion 1.0
 */
@Service
public class CaptchaService {
    private final static String CAPTCHA_KEY = "captcha:";

    @Resource
    RedisCacheUtils redisCacheUtils;


    public void buildCaptcha() {
        buildCaptcha(HttpServletUtils.getResponse());
    }


    public void buildCaptcha(HttpServletResponse response) {
        CaptchaUtils.Captcha captcha = CaptchaUtils.createCaptcha(120, 30);
        String captchaId = Md5Utils.hash(Md5Utils.getUUID());
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        Cookie cookie = new Cookie("captchaId", captchaId);
        cookie.setMaxAge(1800);
        response.addCookie(cookie);

        redisCacheUtils.setCacheObject(CAPTCHA_KEY + captchaId, captcha.getCode(),300);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(captcha.getImage(), "jpg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean valid(String code) {
        return valid(HttpServletUtils.getRequest(), code);
    }


    public boolean valid(HttpServletRequest request, String code) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals("captchaId")) {
                    String key = CAPTCHA_KEY + cookie.getValue();
                    String captcha = redisCacheUtils.getCacheObject(key, String.class);
                    redisCacheUtils.delete(CAPTCHA_KEY + cookie.getValue());
                    if (StringUtils.isNotEmpty(captcha) && StringUtils.isNotEmpty(code) &&
                            code.toLowerCase().equals(captcha.toLowerCase())) {
                        return true;
                    }

                }
            }
        }
        return false;
    }
}