package cn.ffast.web.controller.sys;

import cn.ffast.core.annotations.Logined;
import cn.ffast.core.annotations.Permission;
import cn.ffast.web.entity.sys.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;

import cn.ffast.web.service.sys.IUserRoleService;

import cn.ffast.core.support.BaseCrudController;

/**
 * @description: 系统_用户角色数据接口
 * @copyright:
 * @createTime: 2017-09-13 09:14:49
 * @author: dzy
 * @version: 1.0
 */
@Controller
@RequestMapping("/api/sys/userRole")
@Logined
@Permission(value = "userRole")
public class UserRoleController extends BaseCrudController<UserRole,IUserRoleService,Long> {

    private static Logger logger = LoggerFactory.getLogger(UserRoleController.class);

    @Resource
    private IUserRoleService service;

    @Override
    protected IUserRoleService getService() {
        return this.service;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }


	
}
