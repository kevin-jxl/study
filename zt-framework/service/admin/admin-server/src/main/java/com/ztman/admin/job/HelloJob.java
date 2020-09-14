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
public class HelloJob implements Job {
    @Autowired
    private JobAndTriggerService jobAndTriggerService;

    public HelloJob() {

    }

    @Override
    public void execute(JobExecutionContext context) {
        try {
            log.error("Hello Job执行时间: " + new Date());
            int a = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            //捕获异常可存入执行日志
            String exception = ExceptionUtils.getStackTrace(e);
            //发送邮件和短信
            List<JobAndTrigger> jobs = jobAndTriggerService.getJobByClassName("com.ztman.admin.job.HelloJob");
            if (jobs != null && jobs.size() > 0) {
                JobAndTrigger job = jobs.get(0);
                if (job.getAlarmPhones() != null) {
                    String tel = "";
                    String[] phones = job.getAlarmPhones().split(",");
                    List<String> phonelist = Arrays.asList(phones);
                    if (phonelist != null && phonelist.size() > 0) {
                        for (String str : phonelist) {
                            tel += "," + str;
                        }
                        SmsSendUtil.SmsSend("tel", "定时任务：" + job.getDescription() + "执行失败，请及时检查处理！详细日志请查收邮件");
                    }
                }
                if (job.getAlarmMails() != null) {
                    String[] mails = job.getAlarmMails().split(",");
                    EmailUtils.sendEmail("smtp.163.com", "18653217930@163.com", "fenghai007", mails, "定时任务：" + job.getDescription() + "执行失败！", "错误日志：" + exception);
                }
            }
        }

    }
}  
