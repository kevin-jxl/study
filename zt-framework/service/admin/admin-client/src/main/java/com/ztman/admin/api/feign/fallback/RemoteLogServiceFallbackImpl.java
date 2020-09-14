package com.ztman.admin.api.feign.fallback;

import com.ztman.admin.api.entity.SysLog;
import com.ztman.admin.api.feign.RemoteLogService;
import com.ztman.common.core.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteLogServiceFallbackImpl implements RemoteLogService {

	/**
	 * 保存日志
	 *
	 * @param sysLog
	 * @return R
	 */
	@Override
	public R<Boolean> save(SysLog sysLog) {
		log.error("feign 插入日志失败:{}");
		return null;
	}
}
