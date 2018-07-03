
package cn.ffast.web.controller.sys;

import cn.ffast.core.annotations.CrudConfig;
import cn.ffast.core.support.BaseCrudController;
import cn.ffast.core.vo.ServiceResult;

import cn.ffast.web.entity.sys.ScheduleJobLog;
import cn.ffast.web.service.sys.ScheduleJobLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * @description: 定时任务日志
 * @copyright:
 * @createTime: 2018/6/29 15:23
 * @author：dzy
 * @version：1.0
 */
@RestController
@RequestMapping("/api/sys/scheduleLog")
@CrudConfig(isAsc = false)
public class ScheduleJobLogController extends BaseCrudController<ScheduleJobLog, ScheduleJobLogService, Long> {
    private static Logger logger = LoggerFactory.getLogger(ScheduleJobLogController.class);
    @Resource
    private ScheduleJobLogService scheduleJobLogService;


    /**
     * 定时任务日志信息
     */
    @GetMapping("/info/{id}")
    public ServiceResult info(@PathVariable("id") Long id) {
        ScheduleJobLog log = scheduleJobLogService.selectById(id);

        return new ServiceResult(true).addData("obj", log);
    }

    @Override
    protected ScheduleJobLogService getService() {
        return scheduleJobLogService;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
