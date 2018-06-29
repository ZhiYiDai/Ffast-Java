package cn.ffast.web.service.impl.sys;

import cn.ffast.core.support.CrudServiceImpl;
import cn.ffast.web.dao.sys.ScheduleJobMapper;
import cn.ffast.web.entity.sys.ScheduleJob;
import cn.ffast.web.schedule.ScheduleStatus;
import cn.ffast.web.schedule.ScheduleUtils;
import cn.ffast.web.service.sys.ScheduleJobService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

@Service("scheduleJobService")
public class ScheduleJobServiceImpl extends CrudServiceImpl<ScheduleJobMapper, ScheduleJob, Long> implements ScheduleJobService {
    @Resource
    private Scheduler scheduler;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<ScheduleJob> scheduleJobList = this.selectList(null);
        for (ScheduleJob scheduleJob : scheduleJobList) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getId());
            //如果不存在，则创建
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }


    @Override
    public void run(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.run(scheduler, this.selectById(jobId));
        }
    }

    @Override
    public void pause(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.pauseJob(scheduler, jobId);
        }
        updateStatus(jobIds, ScheduleStatus.PAUSE.getValue());
    }

    private void updateStatus(Long[] jobIds,int value){
        EntityWrapper ew = new EntityWrapper<ScheduleJob>();
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setStatus(value);
        ew.in("id", jobIds);
        baseMapper.update(scheduleJob, ew);
    }

    @Override
    public void resume(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.resumeJob(scheduler, jobId);
        }
        updateStatus(jobIds,ScheduleStatus.NORMAL.getValue());
    }


    public void Test() {
        System.out.println("ScheduleJobServiceImpl test");
    }

}
