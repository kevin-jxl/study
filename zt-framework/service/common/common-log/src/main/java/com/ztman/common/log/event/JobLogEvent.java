package com.ztman.common.log.event;

import com.ztman.admin.api.entity.JobLog;
import org.springframework.context.ApplicationEvent;

/**
 * 系统日志事件
 */
public class JobLogEvent extends ApplicationEvent {

	public JobLogEvent(JobLog source) {
		super(source);
	}
}
