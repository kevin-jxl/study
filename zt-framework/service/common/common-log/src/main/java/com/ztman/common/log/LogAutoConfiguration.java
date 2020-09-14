
package com.ztman.common.log;

import com.ztman.admin.api.feign.RemoteLogService;
import com.ztman.common.log.aspect.SysLogAspect;
import com.ztman.common.log.event.SysLogListener;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志自动配置
 */
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
@EnableFeignClients({"com.ztman.admin.api.feign"})
public class LogAutoConfiguration {
	private final RemoteLogService remoteLogService;

	public SysLogListener sysLogListener() {
		return new SysLogListener(remoteLogService);
	}

	@Bean
	public SysLogAspect sysLogAspect() {
		return new SysLogAspect();
	}

}
