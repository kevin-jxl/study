package com.ztman.common.log.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;

import com.ztman.admin.api.entity.SysLog;
import com.ztman.common.core.constant.CommonConstant;
import com.ztman.common.security.util.SecurityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统日志工具类
 *
 */
public class SysLogUtils {

	private static final String PASSWORD = "password";

	public static SysLog getSysLog() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		StringBuilder params = new StringBuilder();
		request.getParameterMap().forEach((key, values) -> {
			params.append(key).append("＝");
			if (PASSWORD.equalsIgnoreCase(key)) {
				params.append("******");
			} else {
				params.append(ArrayUtil.toString(values));
			}
			params.append("＆");
		});

		SysLog sysLog = new SysLog();
		sysLog.setCreateBy(SecurityUtils.getUser());
		sysLog.setType(CommonConstant.STATUS_NORMAL);
		sysLog.setRemoteAddr(HttpUtil.getClientIP(request));
		sysLog.setRequestUri(URLUtil.getPath(request.getRequestURI()));
		sysLog.setMethod(request.getMethod());
		sysLog.setUserAgent(request.getHeader("user-agent"));
		sysLog.setParams(HttpUtil.toParams(request.getParameterMap()));
		sysLog.setServiceId(SecurityUtils.getClientId());
		return sysLog;
	}
}
