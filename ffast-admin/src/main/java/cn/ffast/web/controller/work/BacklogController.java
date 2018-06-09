package cn.ffast.web.controller.work;


import cn.ffast.core.annotations.CrudConfig;
import cn.ffast.core.annotations.Logined;
import cn.ffast.core.support.BaseCrudController;
import cn.ffast.web.entity.work.Backlog;
import cn.ffast.web.service.work.IBacklogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @description: 待办事项数据接口
 * @copyright:
 * @createTime: 2018-06-09 11:20:16
 * @author: dzy
 * @version: 1.0
 */
@Controller
@RequestMapping("/api/work/backlog")
@CrudConfig(sortField = "start_time",isAsc = true)
@Logined
public class BacklogController extends BaseCrudController<Backlog, IBacklogService, Long> {

    private static Logger logger = LoggerFactory.getLogger(BacklogController.class);

    @Resource
    private IBacklogService service;

    @Override
    protected IBacklogService getService() {
        return this.service;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }


}
