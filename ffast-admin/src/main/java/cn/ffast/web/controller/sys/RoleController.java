package cn.ffast.web.controller.sys;


import cn.ffast.core.annotations.CrudConfig;
import cn.ffast.core.annotations.Log;
import cn.ffast.core.annotations.Logined;
import cn.ffast.core.annotations.Permission;
import cn.ffast.core.vo.ServiceResult;
import cn.ffast.web.entity.sys.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import cn.ffast.web.service.sys.IRoleService;
import cn.ffast.core.support.BaseCrudController;


/**
 * @description: 系统_角色数据接口
 * @copyright:
 * @createTime: 2017-09-13 09:14:49
 * @author: dzy
 * @version: 1.0
 */
@Controller
@RequestMapping("/api/sys/role")
@Logined
@Permission(value = "role")
@Log("角色管理")
public class RoleController extends BaseCrudController<Role, IRoleService, Long> {

    private static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Resource
    private IRoleService service;

    @Override
    protected IRoleService getService() {
        return this.service;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

}
