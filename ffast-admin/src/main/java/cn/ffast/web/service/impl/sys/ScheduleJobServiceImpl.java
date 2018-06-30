package cn.ffast.web.service.impl.sys;

import cn.ffast.core.support.CrudServiceImpl;
import cn.ffast.core.vo.ServiceResult;
import cn.ffast.web.dao.sys.ScheduleJobMapper;
import cn.ffast.web.entity.sys.ScheduleJob;
import cn.ffast.core.constant.ScheduleStatus;
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
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
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

    private void updateStatus(Long[] jobIds, int value) {
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
        updateStatus(jobIds, ScheduleStatus.NORMAL.getValue());
    }

    @Override
    protected ServiceResult updateAfter(ScheduleJob m, ScheduleJob oldM) {
        createOrUpdateStatus(m);
        oldM.setBeanName(m.getBeanName());
        oldM.setStatus(m.getStatus());
        oldM.setCronExpression(m.getCronExpression());
        oldM.setMethodName(m.getMethodName());
        oldM.setParams(m.getParams());
        ScheduleUtils.updateScheduleJob(scheduler, oldM);
        return null;
    }

    private void createOrUpdateStatus(ScheduleJob m) {
        if (m.getStatus() != null) {
            if (ScheduleStatus.NORMAL.getValue() == m.getStatus().intValue()) {
                ScheduleUtils.resumeJob(scheduler, m.getId());
            } else if (ScheduleStatus.PAUSE.getValue() == m.getStatus().intValue()) {
                ScheduleUtils.pauseJob(scheduler, m.getId());
            }
        }
    }


    @Override
    protected ServiceResult createAfter(ScheduleJob m) {
        ScheduleUtils.createScheduleJob(scheduler, m);
        createOrUpdateStatus(m);
        return null;
    }


    public void Test() {
        System.out.println("ScheduleJobServiceImpl test");
    }

}
