package cn.ffast.web.controller.sys;


import cn.ffast.core.annotations.Log;
import cn.ffast.core.annotations.Logined;
import cn.ffast.core.annotations.Permission;
import cn.ffast.core.auth.OperatorUtils;
import cn.ffast.web.service.sys.IResService;
import cn.ffast.core.vo.ServiceResult;
import cn.ffast.web.entity.sys.RoleRes;
import cn.ffast.core.vo.ServiceRowsResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import cn.ffast.web.service.sys.IRoleResService;

import cn.ffast.core.support.BaseCrudController;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 系统_角色资源数据接口
 * @copyright:
 * @createTime: 2017-09-13 09:14:49
 * @author: dzy
 * @version: 1.0
 */
@Controller
@RequestMapping("/api/sys/roleRes")
@Logined
@Permission(value = "roleRes")
@Log("角色资源")
public class RoleResController extends BaseCrudController<RoleRes, IRoleResService, Long> {

    private static Logger logger = LoggerFactory.getLogger(RoleResController.class);

    @Resource
    private IRoleResService service;
    @Resource
    private IResService resService;

    @Override
    protected IRoleResService getService() {
        return this.service;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Resource
    private OperatorUtils operatorUtils;

    /**
     * 保存角色资源菜单
     *
     * @param ids
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveRes")
    public ServiceResult saveRes(String ids, Long roleId) {
        // Demo限制（可以删除）：不允许修改超级管理员账户
        if (roleId.intValue() == 1) {
            return new ServiceResult(false).setMessage("不能修改超级管理员账户");
        }
        return service.saveRes(ids, roleId);
    }

    /**
     * 获得所有资源菜单与角色所拥有的资源列表
     *
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getRoleRes")
    public ServiceResult getRoleResIds(Long roleId) {
        ServiceRowsResult result = new ServiceRowsResult(false);
        result.addData("rows", resService.selectList(null, new String[]{"id", "name", "parent_id", "res_type"}));
        result.addData("selected", service.getRoleResIds(roleId));
        result.setSuccess(true);
        return result;
    }
}
