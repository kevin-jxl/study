package com.ztman.common.log.aspect;

import com.ztman.common.log.annotation.JobLog;
import com.ztman.tools.sms.SmsSendUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.quartz.JobExecutionContext;

import java.util.ArrayList;
import java.util.List;


/**
 * 定时任务日志入库 || 告警捕获
 * 暂时弃用2018-11-14
 */
@Aspect
@Slf4j
public class JobLogAspect {

    @Around("@annotation(jobLog)")
    public Object aroundWxApi(ProceedingJoinPoint point, JobLog jobLog) throws Throwable {
        String strClassName = point.getTarget().getClass().getName();
        String strMethodName = point.getSignature().getName();
        log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);
        //定义返回值
        java.lang.Object result = null;
        String jobdes = null;
        JobExecutionContext context = null;
        try {
            //获取参数
            Object[] args = point.getArgs();
            context = (JobExecutionContext) args[0];
            //获取任务名称
            jobdes = context.getJobDetail().getDescription();
            result = point.proceed(args);
        } catch (Exception e) {
            String tel = "";
            //获取堆栈信息
            String exception = ExceptionUtils.getStackTrace(e);
            //发送邮件和短信
            List<String> phones = new ArrayList<>();
            phones.add("18653217930");
            if (phones != null && phones.size() > 0) {
                for (String str : phones) {
                    tel += "," + str;
                }
                SmsSendUtil.SmsSend("tel", "定时任务：" + "执行定时任务出现异常：" + jobLog.value());
            }
        }
        return result;
    }

}
