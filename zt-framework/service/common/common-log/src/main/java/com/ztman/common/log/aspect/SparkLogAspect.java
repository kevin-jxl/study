package com.ztman.common.log.aspect;

import com.ztman.admin.api.entity.SysLog;
import com.ztman.admin.api.feign.RemoteLogService;
import com.ztman.common.log.annotation.SparkLog;
import com.ztman.common.log.util.SysLogUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * spark操作日志切面逻辑
 *
 */
@Aspect
@Slf4j
public class SparkLogAspect {
	@Autowired
	RemoteLogService remoteLogService;
	@Around("@annotation(sparklog)")
	public Object aroundLogApi(ProceedingJoinPoint point, SparkLog sparklog) throws Throwable {
		String strClassName = point.getTarget().getClass().getName();
		String strMethodName = point.getSignature().getName();
		sparklog.title();
		log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);
		SysLog logVo = SysLogUtils.getSysLog();
		logVo.setTitle(sparklog.title());
		logVo.setType("0");
		// 发送异步日志事件
		Long startTime = System.currentTimeMillis();
		Object obj = point.proceed();
		Long endTime = System.currentTimeMillis();
		logVo.setTime(endTime - startTime);
		remoteLogService.save(logVo);

//		Object obj = point.proceed();//执行业务逻辑

		return obj;
	}

}
