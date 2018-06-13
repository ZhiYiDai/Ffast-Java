package cn.ffast.web.controller.sys;


import cn.ffast.core.annotations.CrudConfig;
import cn.ffast.core.annotations.Logined;
import cn.ffast.core.annotations.Permission;
import cn.ffast.core.auth.OperatorUtils;
import cn.ffast.web.entity.sys.User;
import cn.ffast.web.entity.sys.UserRole;
import cn.ffast.web.service.sys.IUserRoleService;
import cn.ffast.core.utils.FStringUtil;
import cn.ffast.core.auth.OperatorBase;
import cn.ffast.core.vo.ServiceResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import cn.ffast.web.service.sys.IUserService;
import cn.ffast.core.support.BaseCrudController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


/**
 * @description: 系统_用户数据接口
 * @copyright:
 * @createTime: 2017-09-13 09:14:49
 * @author: dzy
 * @version: 1.0
 */
@Controller
@RequestMapping("/api/sys/user")
@Logined
@Permission(value = "user")
@CrudConfig(updateAllColumn = true, updateIgnoreProperties = {"pwd", "salt"})
public class UserController extends BaseCrudController<User, IUserService, Long> {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private IUserService service;
    @Resource
    private IUserRoleService userRoleService;

    @Resource
    private OperatorUtils operatorUtils;

    @Override
    protected IUserService getService() {
        return this.service;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    protected void createAfter(User m, ServiceResult result) {
        updateRole(m, result);
    }

    @Override
    protected void updateAfter(User m, ServiceResult result) {
        updateRole(m, result);
    }


    /**
     * 更新角色
     *
     * @param m
     * @param result
     */
    private void updateRole(User m, ServiceResult result) {
        EntityWrapper ew = new EntityWrapper<User>();
        ew.eq("user_id", m.getId());
        if (!StringUtils.isEmpty(m.getRoleId())) {
            if (m.getId() != null) {
                String[] idArray = FStringUtil.split(m.getRoleId(), ",");
                Long creatorId = getLoginUserId();
                List<UserRole> urList = new ArrayList<>();
                for (int i = 0; i < idArray.length; i++) {
                    UserRole ur = new UserRole();
                    ur.setUserId(m.getId());
                    ur.setRoleId(new Long(idArray[i]));
                    ur.setCreatorId(creatorId);
                    urList.add(ur);
                }
                userRoleService.delete(ew);
                userRoleService.insertBatch(urList);
            }
        } else {
            userRoleService.delete(ew);
        }

    }

    /**
     * 重置密码
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/reseting", method = RequestMethod.POST)
    @ResponseBody
    @Permission("reseting")
    public ServiceResult resetting(Long id) {
        ServiceResult result = getService().reseting(id);
        return result;
    }

    /**
     * 修改密码
     *
     * @param pwd     原密码
     * @param newpwd  新密码
     * @param newpwd2 重复新密码
     * @return
     */
    @RequestMapping(value = "/respwd", method = RequestMethod.POST)
    @ResponseBody
    public ServiceResult respwd(String pwd, String newpwd, String newpwd2) {
        // Demo限制（可以删除）：不允许修改超级管理员账户
        if (operatorUtils.getLoginUserId().intValue() == 1) {
            return new ServiceResult(false).setMessage("不能修改超级管理员账户");
        }
        return service.respwd(operatorUtils.getLoginUserId(), pwd, newpwd, newpwd2);
    }


    @Override
    protected ServiceResult updateBefore(User m) {
        // Demo限制（可以删除）：修改用户前判断，不允许修改admin账户
        if (m.getId() == 1) {
            return new ServiceResult(false).setMessage("不能修改admin");
        }
        return null;
    }


}
