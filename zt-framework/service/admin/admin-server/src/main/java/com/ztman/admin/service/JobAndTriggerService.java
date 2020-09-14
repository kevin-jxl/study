package com.ztman.admin.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ztman.admin.api.entity.JobAndTrigger;
import com.ztman.common.core.util.Query;

import java.util.List;

public interface JobAndTriggerService  extends IService<JobAndTrigger> {
	/**
	 * 分页查询定时任务信息（含有触发器信息）
	 *
	 * @param query 查询条件
	 * @return
	 */
	Page getJobAndTriggerDetails(Query query);

	/**
	 * 根据任务类名查找任务信息
	 * @param jobClassName
	 * @return
	 */
	List<JobAndTrigger> getJobByClassName( Object jobClassName );
}
