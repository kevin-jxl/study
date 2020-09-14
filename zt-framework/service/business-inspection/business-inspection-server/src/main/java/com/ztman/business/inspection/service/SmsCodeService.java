package com.ztman.business.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import com.ztman.business.inspection.client.entity.SmsCode;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.common.core.util.Query;
/**
 * 短信验证码 服务接口层
 *
 * @author zxx
 * @date 2020-08-29 11:11:37
 */
public interface SmsCodeService extends IService<SmsCode> {
     
	public String getCodeBy(String phone, String type);

	public boolean validate(String phone, String type, String code);

}

