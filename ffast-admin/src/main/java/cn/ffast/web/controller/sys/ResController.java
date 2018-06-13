package cn.ffast.web.controller.sys;

import cn.ffast.core.annotations.CrudConfig;
import cn.ffast.core.annotations.Log;
import cn.ffast.core.annotations.Logined;
import cn.ffast.core.annotations.Permission;
import cn.ffast.core.vo.ServiceResult;
import cn.ffast.web.entity.sys.Res;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import cn.ffast.web.service.sys.IResService;
import cn.ffast.core.support.BaseCrudController;

/**
 * @description: 系统_资源数据接口
 * @copyright:
 * @createTime: 2017-09-13 09:14:49
 * @author: dzy
 * @version: 1.0
 */
@Controller
@RequestMapping("/api/sys/res")
@CrudConfig(updateAllColumn = true, retrievePermission = "")
@Permission(value = "res")
@Logined
@Log("菜单权限")
public class ResController extends BaseCrudController<Res, IResService, Long> {

    private static Logger logger = LoggerFactory.getLogger(ResController.class);

    @Resource
    private IResService service;

    @Override
    protected IResService getService() {
        return this.service;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }


    @Override
    protected ServiceResult createBefore(Res m) {
        // Demo限制 可以删除
        return new ServiceResult(false).setMessage("暂时关闭该功能！");
    }

    @Override
    protected ServiceResult deleteBefore(String ids) {
        // Demo限制 可以删除
        return new ServiceResult(false).setMessage("暂时关闭该功能！");
    }

    @Override
    protected ServiceResult updateBefore(Res m) {
        // Demo限制 可以删除
        return new ServiceResult(false).setMessage("暂时关闭该功能！");
    }

    @Override
    protected void createAfter(Res m, ServiceResult result) {
        if ("true".equals(getRequestParamString("addBaseCrud")) && m.getResType() == 1) {
            service.addBaseCrud(m);
        }
    }
}
