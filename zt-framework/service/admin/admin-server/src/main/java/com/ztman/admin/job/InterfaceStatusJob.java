package com.ztman.admin.job;

import com.ztman.admin.api.entity.JobAndTrigger;
import com.ztman.admin.service.JobAndTriggerService;
import com.ztman.tools.email.EmailUtils;
import com.ztman.tools.sms.SmsSendUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 测试JOB
 */
@Slf4j
public class InterfaceStatusJob implements Job {
    @Autowired
    private JobAndTriggerService jobAndTriggerService;

    public InterfaceStatusJob() {

    }

    @Override
    public void execute(JobExecutionContext context) {

            log.error("InterfaceStatusJob 执行时间: " + new Date());

    }
}  
