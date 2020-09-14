package com.ztman.common.log.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ztman.admin.api.entity.SysLog;
import com.ztman.admin.api.feign.RemoteLogService;
import com.ztman.common.core.util.SpringContextHolder;
import com.ztman.common.log.event.SysLogEvent;
import com.ztman.common.log.util.SysLogUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

/**
 * 操作日志使用spring event异步入库
 *
 */
@Aspect
@Slf4j
public class SysLogAspect {
	@Autowired
	private RemoteLogService remoteLogService;
	@Around("@annotation(sysLog)")
	public Object aroundWxApi(ProceedingJoinPoint point, com.ztman.common.log.annotation.SysLog sysLog) throws Throwable {
		String strClassName = point.getTarget().getClass().getName();
		String strMethodName = point.getSignature().getName();
		log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);
		SysLog logVo = SysLogUtils.getSysLog();
		logVo.setTitle(sysLog.value());
		// 发送异步日志事件
		Long startTime = System.currentTimeMillis();
		Object obj = point.proceed();
		Long endTime = System.currentTimeMillis();
		logVo.setTime(endTime - startTime);
		remoteLogService.save(logVo);
		//推送事件
		SpringContextHolder.publishEvent(new SysLogEvent(logVo));
		return obj;
	}

}
