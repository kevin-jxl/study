package com.ztman.admin.service.impl;

import com.ztman.admin.api.entity.JobAlarmConfig;
import com.ztman.admin.mapper.JobAlarmConfigMapper;
import com.ztman.admin.service.JobAlarmConfigService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;



@Service("jobAlarmConfigService")
public class JobAlarmConfigServiceImpl extends ServiceImpl<JobAlarmConfigMapper, JobAlarmConfig> implements JobAlarmConfigService {

}
