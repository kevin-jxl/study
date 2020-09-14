package com.ztman.admin.api.feign;

import com.ztman.admin.api.feign.fallback.RemoteLogServiceFallbackImpl;
import com.ztman.admin.api.entity.SysLog;
import com.ztman.common.core.constant.ServiceNameConstant;
import com.ztman.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = ServiceNameConstant.UMPS_SERVICE, fallback = RemoteLogServiceFallbackImpl.class)
public interface RemoteLogService {
	/**
	 * 保存日志
	 *
	 * @param sysLog 日志实体
	 * @return succes、false
	 */
	@PostMapping("/log")
	R<Boolean> save(@RequestBody SysLog sysLog);
}
