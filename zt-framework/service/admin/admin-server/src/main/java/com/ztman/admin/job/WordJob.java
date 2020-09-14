package com.ztman.admin.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * 测试JOB
 */
@Slf4j
public class WordJob implements Job {


    public WordJob() {
          
    }  
     @Override
    public void execute(JobExecutionContext context)  
        throws JobExecutionException {  
        log.error("World Job执行时间: " + new Date());
          
    }  
}  
