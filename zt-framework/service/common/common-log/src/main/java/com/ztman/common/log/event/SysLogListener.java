package com.ztman.common.log.event;


import com.ztman.admin.api.entity.SysLog;
import com.ztman.admin.api.feign.RemoteLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


/**
 * 异步监听日志事件
 */
@Slf4j
@AllArgsConstructor
@Component
public class SysLogListener {
	private final RemoteLogService remoteLogService;

//	@Async
//	@Order
	@EventListener(SysLogEvent.class)
	public void saveSysLog(SysLogEvent event) {
		SysLog sysLog = (SysLog) event.getSource();
		remoteLogService.save(sysLog);
	}
}
