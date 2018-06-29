
package cn.ffast.web.controller.sys;

import cn.ffast.core.vo.ServiceResult;

import cn.ffast.web.entity.sys.ScheduleJobLog;
import cn.ffast.web.service.sys.ScheduleJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
/**
 * @description: 定时任务日志
 * @copyright:
 * @createTime: 2018/6/29 15:23
 * @author：dzy
 * @version：1.0
 */
@RestController
@RequestMapping("/api/sys/scheduleLog")
public class ScheduleJobLogController {
    @Autowired
    private ScheduleJobLogService scheduleJobLogService;


    /**
     * 定时任务日志信息
     */
    @GetMapping("/info/{id}")
    public ServiceResult info(@PathVariable("id") Long id) {
        ScheduleJobLog log = scheduleJobLogService.selectById(id);

        return new ServiceResult(true).addData("obj", log);
    }
}
