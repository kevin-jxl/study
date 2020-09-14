package com.ztman.admin.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.admin.api.entity.JobAndTrigger;
import com.ztman.admin.mapper.JobAndTriggerMapper;
import com.ztman.admin.service.JobAndTriggerService;
import com.ztman.admin.service.*;
import com.ztman.common.core.util.Query;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ztman
 * @date 2017/10/31
 */
@Slf4j
@Service
@AllArgsConstructor
public class JobAndTriggerServiceImpl extends ServiceImpl<JobAndTriggerMapper, JobAndTrigger> implements JobAndTriggerService {
	private final JobAndTriggerMapper jobAndTriggerMapper;
	@Override
	public Page getJobAndTriggerDetails(Query query) {
		Object description = query.getCondition().get("description");
		List<JobAndTrigger> records=jobAndTriggerMapper.getJobAndTriggerDetails(query,description);
		query.setRecords(records);

		return query;
	}

	@Override
	public List<JobAndTrigger> getJobByClassName(Object jobClassName) {
		List<JobAndTrigger> records=jobAndTriggerMapper.getJobByClassName(jobClassName);
		return records;
	}


}
