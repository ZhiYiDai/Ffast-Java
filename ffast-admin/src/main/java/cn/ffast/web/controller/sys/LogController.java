package cn.ffast.web.controller.sys;

import cn.ffast.core.annotations.Logined;
import cn.ffast.core.annotations.Permission;
import cn.ffast.web.entity.sys.Log;
import cn.ffast.web.service.sys.ILogService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import javax.annotation.Resource;

import cn.ffast.core.support.BaseCrudController;

/**
 * @description: 操作日志表数据接口
 * @copyright:
 * @createTime: 2017-11-14 14:48:11
 * @author: dzy
 * @version: 1.0
 */
@Controller
@RequestMapping("/api/sys/log")
@Logined
@Permission(value = "sysLog")
public class LogController extends BaseCrudController<Log,ILogService,Long> {

    private static Logger logger = LoggerFactory.getLogger(ResController.class);

    @Resource
    private ILogService service;

    @Override
    protected ILogService getService() {
        return this.service;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }


	
}
