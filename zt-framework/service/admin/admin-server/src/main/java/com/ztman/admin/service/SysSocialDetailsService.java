package com.ztman.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.ztman.admin.api.entity.SysSocialDetails;

import java.util.Map;

/**
 * 系统社交登录账号表
 *
 */
public interface SysSocialDetailsService extends IService<SysSocialDetails> {

	/**
	 * 绑定社交账号
	 *
	 * @param state 类型
	 * @param code  code
	 * @return
	 */
	Boolean bindSocial(String state, String code);

	/**
	 * 通过客户端信息查询openId
	 *
	 * @param inStr appid @ code
	 * @return
	 */
	Map<String, String> findOpenId(String inStr);
}

