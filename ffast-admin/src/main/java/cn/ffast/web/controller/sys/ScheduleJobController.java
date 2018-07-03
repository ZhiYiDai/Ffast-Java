package cn.ffast.web.controller.sys;

import cn.ffast.core.annotations.Log;
import cn.ffast.core.annotations.Permission;
import cn.ffast.core.support.BaseCrudController;
import cn.ffast.core.vo.ServiceResult;
import cn.ffast.web.entity.sys.ScheduleJob;
import cn.ffast.web.service.sys.ScheduleJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
/**
 * @description: 定时任务
 * @copyright:
 * @createTime: 2018/6/29 15:23
 * @author：dzy
 * @version：1.0
 */
@RestController
@RequestMapping("/api/sys/schedule")
@Permission("schedule")
public class ScheduleJobController extends BaseCrudController<ScheduleJob, ScheduleJobService, Long> {
    private static Logger logger = LoggerFactory.getLogger(ScheduleJobController.class);
    @Resource
    private ScheduleJobService service;

    /**
     * 定时任务信息
     */
    @GetMapping("/info/{id}")
    @Permission("info")
    public ServiceResult info(@PathVariable("id") Long id) {
        ScheduleJob schedule = service.selectById(id);
        return new ServiceResult(true).addData("obj", schedule);
    }


    /**
     * 立即执行任务
     */
    @Log("立即执行任务")
    @PostMapping("/run")
    @Permission("run")
    public ServiceResult ServiceResultun(Long[] ids) {
        service.run(ids);
        return new ServiceResult(true);
    }

    /**
     * 暂停定时任务
     */
    @Log("暂停定时任务")
    @PostMapping("/pause")
    @Permission("pause")
    public ServiceResult pause(Long[] ids) {
        service.pause(ids);
        return new ServiceResult(true);
    }

    /**
     * 恢复定时任务
     */
    @Log("恢复定时任务")
    @PostMapping("/resume")
    @Permission("resume")
    public ServiceResult ServiceResultesume(Long[] ids) {
        service.resume(ids);
        return new ServiceResult(true);
    }

    @Override
    protected ScheduleJobService getService() {
        return service;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }
}
