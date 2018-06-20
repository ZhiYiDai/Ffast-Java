package cn.ffast.web.service.impl.sys;


import cn.ffast.core.service.CaptchaService;
import cn.ffast.core.support.IAuthService;
import cn.ffast.core.auth.OperatorUtils;
import cn.ffast.core.utils.CollectionUtils;
import cn.ffast.core.vo.ServiceResult;
import cn.ffast.web.entity.sys.Role;
import cn.ffast.web.entity.sys.User;
import cn.ffast.web.service.sys.*;
import cn.ffast.web.vo.Operator;
import cn.ffast.web.vo.RoleMenuPerms;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AuthServiceImpl implements IAuthService {
    private static Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Value("${auth.captchaEnable}")
    private boolean captchaEnable;

    @Resource
    private CaptchaService captchaService;
    @Resource
    private IUserService userService;
    @Resource
    private IUserRoleService userRoleService;
    @Resource
    private IRoleService roleService;
    @Resource
    private IRoleResService roleResourcesService;
    @Resource
    private OperatorUtils operatorUtils;


    @Override
    public ServiceResult login(String username, String password, String captcha, boolean getMenuPerms) {
        ServiceResult result = new ServiceResult(false);
        logger.debug("login username:%s password:%s captcha%s", username, password, captcha);
        if (captchaEnable) {
            if (StringUtils.isEmpty(captcha)) {
                result.setMessage("验证码不为空！");
                return result;
            } else if (!captchaService.valid(captcha)) {
                result.setMessage("验证码错误！");
                return result;
            }
        }
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            result.setMessage("用户名和密码不能为空！");
            return result;
        }
        User user = userService.verifyUser(username, password);
        userService.updateLoginResult(username, false);
        if (user == null) {
            logger.info("用户名不存在或密码错误!");
            result.setMessage("用户名不存在或密码错误！");
            return result;
        }
        logger.debug("用户当前状态：（1为锁定 0为未锁定）" + user.getIsLock());
        if (new Integer(1).equals(user.getIsLock())) {
            logger.info("该用户已经被锁定!");
            result.setMessage("该用户已经被锁定！无法登陆！");
            return result;
        }
        joinData(user, result, getMenuPerms);
        logger.debug(user.getUsername() + "【登录】");
        result.setSuccess(true);
        return result;
    }

    private void joinData(User user, ServiceResult result, Boolean getMenuPerms) {
        userService.updateLoginResult(user.getUsername(), true);
        Operator curLoginUser = new Operator();
        curLoginUser.setUserName(user.getUsername());
        curLoginUser.setUserId(user.getId());
        curLoginUser.setName(user.getName());
        HashMap<String, Object> userData = new HashMap(10);
        userData.put("userName", user.getUsername());
        /**
         * 获得角色和角色权限
         */
        List<Role> roles = userRoleService.listByUserId(user.getId(), null);
        if (CollectionUtils.isNotEmpty(roles)) {
            int roleSize = roles.size();
            Set<Long> roleIds = new HashSet<>(roleSize);
            List<String> roleNames = getMenuPerms ? new ArrayList<>(roleSize) : null;
            List<String> mains = getMenuPerms ? new ArrayList<>(roleSize) : null;
            for (Role r : roles) {
                if (r != null) {
                    roleIds.add(r.getId());
                    if (getMenuPerms) {
                        roleNames.add(r.getName());
                        mains.add(r.getMain());
                    }
                }
            }
            curLoginUser.setHasRoleId(roleIds);
            // 是否获取菜单权限
            if (getMenuPerms) {
                userData.put("roleName", roleNames.toString());
                userData.put("main", mains.toString());
                RoleMenuPerms roleMenuPerms = roleResourcesService.getRoleMenuAndPerms(CollectionUtils.arraryToString(roleIds));
                result.addData("menuData", roleMenuPerms.getMenuList());
                result.addData("permsData", roleMenuPerms.getPermsList());
            }
        }
        result.addData("token", operatorUtils.createToken(curLoginUser));
        result.addData("userData", userData);
    }

    @Override
    public ServiceResult getMenuPermsByRoleName(String roleName) {
        ServiceResult result = new ServiceResult(false);
        Operator operator = operatorUtils.getUser(Operator.class);

        if (operator == null) {
            result.setMessage("找不到该角色");
            return result;
        }

        String roleIds = null;
        Set<Long> hasRoleId = operator.getHasRoleId();
        if (StringUtils.isEmpty(roleName)) {
            roleIds = CollectionUtils.arraryToString(hasRoleId);
        } else {
            EntityWrapper<Role> ew = new EntityWrapper<>();
            Role role = roleService.selectOne(ew);
            if (role != null) {
                if (!hasRoleId.contains(role.getId())) {
                    result.setMessage("未拥有的角色");
                    return result;
                }
                roleIds = role.getId().toString();
            } else {
                result.setMessage("找不到该角色");
                return result;
            }
        }
        if (StringUtils.isEmpty(roleIds)) {
            RoleMenuPerms roleMenuPerms = roleResourcesService.getRoleMenuAndPerms(roleIds);
            result.addData("menuData", roleMenuPerms.getMenuList());
            result.addData("permsData", roleMenuPerms.getPermsList());
            result.setSuccess(true);
        }
        return result;
    }


    @Override
    public ServiceResult logout(String token) {
        ServiceResult result = new ServiceResult(true);
        operatorUtils.removeLoginUser(token);
        return result;
    }


}
