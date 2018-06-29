package cn.ffast.web.service.sys;

import cn.ffast.core.support.ICrudService;
import cn.ffast.web.entity.sys.ScheduleJob;


import java.util.Map;
/**
 * @description: 定时任务Service
 * @copyright:
 * @createTime: 2018/6/29 15:23
 * @author：dzy
 * @version：1.0
 */
public interface ScheduleJobService extends ICrudService<ScheduleJob,Long> {


	/**
	 * 立即执行
	 */
	void run(Long[] jobIds);
	
	/**
	 * 暂停运行
	 */
	void pause(Long[] jobIds);
	
	/**
	 * 恢复运行
	 */
	void resume(Long[] jobIds);
}
