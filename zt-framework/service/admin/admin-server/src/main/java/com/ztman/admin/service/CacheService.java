package com.ztman.admin.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author ztman
 * @date 2017/10/31
 */
@Service
public class CacheService {
	@Cacheable(value = "auto_code", key="#params")
	public String insetAutoCode(String params, String verifyCode) {
		return verifyCode;
	}
}
