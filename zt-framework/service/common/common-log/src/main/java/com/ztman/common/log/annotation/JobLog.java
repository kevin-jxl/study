package com.ztman.common.log.annotation;

import java.lang.annotation.*;

/**
 * 定时任务注解：标识定时任务、记录日志
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JobLog {

	/**
	 * 描述
	 *
	 * @return {String}
	 */
	String value();
}
