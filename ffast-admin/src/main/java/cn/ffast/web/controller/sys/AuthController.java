package cn.ffast.web.controller.sys;

import cn.ffast.core.annotations.Logined;
import cn.ffast.core.annotations.Permission;
import cn.ffast.core.auth.OperatorUtils;
import cn.ffast.core.support.BaseController;
import cn.ffast.core.support.IAuthService;
import cn.ffast.core.service.CaptchaService;
import cn.ffast.core.vo.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 用户登录
 * @copyright:
 * @createTime: 2017/9/5 15:17
 * @author：dzy
 * @version：1.0
 */
@Controller
@RequestMapping("/api/auth")
public class AuthController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Resource
    private CaptchaService captchaService;

    @Resource
    IAuthService authService;

    /***
     * 用户登录
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public ServiceResult login(String username, String password, String captcha) {
        return authService.login(username, password, captcha, true);
    }


    /**
     * 是否登录
     *
     * @return
     */
    @RequestMapping(value = "/isLogined")
    @ResponseBody
    @Logined
    public ServiceResult isLogined() {
        return new ServiceResult(true);
    }


    /**
     * 退出登录
     *
     * @return
     */
    @RequestMapping(value = "/logout")
    @ResponseBody
    public ServiceResult logout() {
        return authService.logout(getRequest().getHeader("token"));
    }

    /**
     * 退出登录
     *
     * @return
     */
    @RequestMapping(value = "/captcha")
    public void captcha(HttpServletResponse response) {
        captchaService.buildCaptcha(response);
    }

    /**
     * 获得菜单权限
     *
     * @param roleName 指定登录角色名的菜单权限（为空则默认角色）
     * @return
     */
    @RequestMapping(value = "/getMenuPerms")
    @ResponseBody
    public ServiceResult roleMenuPerms(String roleName) {
        return authService.getMenuPermsByRoleName(roleName);
    }


    @Override
    protected Logger getLogger() {
        return logger;
    }
}