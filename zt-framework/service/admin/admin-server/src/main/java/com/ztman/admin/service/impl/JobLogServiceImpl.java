package com.ztman.admin.service.impl;

import com.ztman.admin.api.entity.JobLog;
import com.ztman.admin.mapper.JobLogMapper;
import com.ztman.admin.service.JobLogService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
@Service("jobLogService")
public class JobLogServiceImpl extends ServiceImpl<JobLogMapper, JobLog> implements JobLogService {

}
