package com.ztman.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.entity.JobAlarmConfig;
import com.ztman.admin.api.entity.JobAndTrigger;
import com.ztman.admin.service.JobAlarmConfigService;
import com.ztman.admin.service.JobAndTriggerService;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/job")
@Slf4j
public class JobAndTriggerController {
//    @Autowired
//    private JobAndTriggerService jobAndTriggerService;
//    @Autowired
//    private JobAlarmConfigService jobAlarmConfigService;
//
//    //加入Qulifier注解，通过名称注入bean
//    @Autowired
//    @Qualifier("Scheduler")
//    private Scheduler scheduler;
//
//    @GetMapping("/jobPage")
//    public Page jobPage(@RequestParam Map<String, Object> params) {
//        return jobAndTriggerService.getJobAndTriggerDetails(new Query(params));
//    }
//
//
//    @PostMapping
//    @Transactional
//    public R<Boolean> job(@RequestBody JobAndTrigger jobAndTrigger) throws Exception {
//        //判断该任务类是否已存在
//       List<JobAndTrigger> lists= jobAndTriggerService.getJobByClassName(jobAndTrigger.getJobClassName().trim());
//       if(lists!=null && lists.size()>0){
//           return new R<>(Boolean.FALSE,"该任务类已存在");
//       }
//        scheduler.start();
//        //构建job信息
//        JobDetail jobDetail = JobBuilder.newJob(getClass(jobAndTrigger.getJobClassName()).getClass()).withIdentity(jobAndTrigger.getJobClassName(), jobAndTrigger.getJobGroup()).withDescription(jobAndTrigger.getDescription()).build();
//
//        //表达式调度构建器(即任务执行的时间)
//        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobAndTrigger.getCronExpression());
//
//        //按新的cronExpression表达式构建一个新的trigger
//        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobAndTrigger.getJobClassName(), jobAndTrigger.getJobGroup())
//                .withSchedule(scheduleBuilder).build();
//        try {
//            //插入标准内容
//            scheduler.scheduleJob(jobDetail, trigger);
//            //插入告警内容
//            JobAlarmConfig jc=new JobAlarmConfig();
//            BeanUtil.copyProperties(jobAndTrigger,jc);
//            jobAlarmConfigService.insertOrUpdate(jc);
//        } catch (SchedulerException e) {
//            log.error("创建定时任务失败" + e);
//            return new R<>(Boolean.FALSE);
//        }
//        return new R<>(Boolean.TRUE);
//    }
//
//    @PostMapping(value = "/pausejob")
//    public R<Boolean> pausejob(@RequestBody JobAndTrigger jobAndTrigger) throws Exception {
//        try {
//            scheduler.pauseJob(JobKey.jobKey(jobAndTrigger.getJobClassName(), jobAndTrigger.getJobGroup()));
//        } catch (SchedulerException e) {
//            return new R<>(Boolean.FALSE);
//        }
//        return new R<>(Boolean.TRUE);
//    }
//
//
//    @PostMapping(value = "/resumejob")
//    public R<Boolean> resumejob(@RequestBody JobAndTrigger jobAndTrigger) throws Exception {
//        try {
//            scheduler.resumeJob(JobKey.jobKey(jobAndTrigger.getJobClassName(), jobAndTrigger.getJobGroup()));
//        } catch (SchedulerException e) {
//            return new R<>(Boolean.FALSE);
//        }
//        return new R<>(Boolean.TRUE);
//    }
//
//
//    @PostMapping(value = "/reschedulejob")
//    public R<Boolean> rescheduleJob(@RequestBody JobAndTrigger jobAndTrigger) throws Exception {
//        try {
//            TriggerKey triggerKey = TriggerKey.triggerKey(jobAndTrigger.getJobClassName(), jobAndTrigger.getJobGroup());
//            // 表达式调度构建器
//            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobAndTrigger.getCronExpression());
//            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
//            // 按新的cronExpression表达式重新构建trigger
//            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
//            // 按新的trigger重新设置job执行
//            scheduler.rescheduleJob(triggerKey, trigger);
//        } catch (SchedulerException e) {
//            log.error("更新定时任务失败"+e);
//            return new R<>(Boolean.FALSE);
//        }
//        return new R<>(Boolean.TRUE);
//    }
//
//    @PostMapping(value = "/deletejob")
//    @Transactional
//    public R<Boolean> deletejob(@RequestBody JobAndTrigger jobAndTrigger) throws Exception {
//        try {
//            scheduler.pauseTrigger(TriggerKey.triggerKey(jobAndTrigger.getJobClassName(), jobAndTrigger.getJobGroup()));
//            scheduler.unscheduleJob(TriggerKey.triggerKey(jobAndTrigger.getJobClassName(), jobAndTrigger.getJobGroup()));
//            scheduler.deleteJob(JobKey.jobKey(jobAndTrigger.getJobClassName(), jobAndTrigger.getJobGroup()));
//            //删除告警配置
//            jobAlarmConfigService.deleteById(jobAndTrigger.getId());
//        } catch (SchedulerException e) {
//            log.error("删除定时任务失败"+e);
//            return new R<>(Boolean.FALSE);
//        }
//        return new R<>(Boolean.TRUE);
//
//
//    }
//
//    public static Job getClass(String classname) throws Exception {
//        Class<?> class1 = Class.forName(classname);
//        return (Job) class1.newInstance();
//    }


}
