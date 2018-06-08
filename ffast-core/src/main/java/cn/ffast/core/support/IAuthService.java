package cn.ffast.core.support;


import cn.ffast.core.vo.ServiceResult;

import javax.servlet.http.HttpServletResponse;

public interface IAuthService {
    /**
     * 登录接口
     * @param username  账户
     * @param password  密码
     * @param captcha   验证码
     * @param getMenuPerms 同时获得菜单权限
     * @return
     */
    ServiceResult login(String username, String password, String captcha, boolean getMenuPerms);

    /**
     * 退出登录
     *
     * @param token
     * @return
     */
    ServiceResult logout(String token);

    /**
     * 获取当前登录账户角色菜单权限
     * @param roleName
     * @return
     */
    ServiceResult getMenuPermsByRoleName(String roleName);
}
