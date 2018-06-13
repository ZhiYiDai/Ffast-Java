package cn.ffast.web.controller.sys;

import cn.ffast.core.annotations.CrudConfig;
import cn.ffast.core.annotations.Log;
import cn.ffast.core.annotations.Logined;
import cn.ffast.core.annotations.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import cn.ffast.web.entity.sys.DictType;
import cn.ffast.web.service.sys.IDictTypeService;

import cn.ffast.core.support.BaseCrudController;

/**
 * @description: 基础字典类型数据接口
 * @copyright:
 * @createTime: 2017-09-13 09:14:49
 * @author: dzy
 * @version: 1.0
 */
@Controller
@RequestMapping("/api/sys/dictType")
@Logined
@Permission(value = "dict")
@Log("字典分类")
public class DictTypeController extends BaseCrudController<DictType,IDictTypeService,Long> {

    private static Logger logger = LoggerFactory.getLogger(DictTypeController.class);

    @Resource
    private IDictTypeService service;

    @Override
    protected IDictTypeService getService() {
        return this.service;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }


	
}
