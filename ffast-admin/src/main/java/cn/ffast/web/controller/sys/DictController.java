package cn.ffast.web.controller.sys;

import cn.ffast.core.annotations.CrudConfig;
import cn.ffast.core.annotations.Log;
import cn.ffast.core.annotations.Logined;
import cn.ffast.core.annotations.Permission;
import cn.ffast.core.vo.ServiceResult;
import cn.ffast.web.entity.sys.Dict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import cn.ffast.web.service.sys.IDictService;

import cn.ffast.core.support.BaseCrudController;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 字典数据接口
 * @copyright:
 * @createTime: 2017-09-13 09:14:49
 * @author: dzy
 * @version: 1.0
 */
@Logined
@Controller
@RequestMapping("/api/sys/dict")
@Permission(value = "dict")
@Log("字典")
public class DictController extends BaseCrudController<Dict, IDictService, Long> {

    private static Logger logger = LoggerFactory.getLogger(DictController.class);

    @Resource
    private IDictService service;

    @Override
    protected IDictService getService() {
        return this.service;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }


    /**
     * 获得字典
     *
     * @param type
     * @param isName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get")
    public ServiceResult getDict(String type, Boolean isName) {
        return getService().getDict(type, isName);
    }
}
