package com.ztman.business.inspection.service.impl;
 

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
 
import com.ztman.business.inspection.mapper.SmsCodeMapper;
import com.ztman.business.inspection.client.entity.SmsCode;
import com.ztman.business.inspection.service.SmsCodeService;


@Service("smsCodeService")
public class SmsCodeServiceImpl extends ServiceImpl<SmsCodeMapper, SmsCode> implements SmsCodeService {

    @Autowired
    private SmsCodeMapper smsCodeMapper;

     

	@Override
	public String getCodeBy(String phone, String type) {
		// TODO Auto-generated method stub. 
		String code =  (int)(new Random().nextDouble()*100000)+"";//获取6位数字验证码
		LocalDateTime current = LocalDateTime.now();
		SmsCode smsCode = new SmsCode();
		smsCode.setCode(code);
		smsCode.setPhoneNumber(phone);
		smsCode.setType(type);
		smsCode.setExpiresTime(current.plusMinutes(5));//当前时间+5分钟
		smsCode.setCreateTime(current);
		
		smsCodeMapper.insert(smsCode); 
		return code;
	}

	@Override
	public boolean validate(String phone, String type, String code) {
		// TODO Auto-generated method stub
    	SmsCode smsCode = new SmsCode();
    	smsCode.setCode(code);
    	smsCode.setPhoneNumber(phone);
    	smsCode.setType(type);
    	List<SmsCode> smsCodes = smsCodeMapper.selectSmsCodeList(smsCode);
    	for (SmsCode item : smsCodes) {
    		Duration duration =Duration.between(LocalDateTime.now(),item.getExpiresTime()); 
    		if( duration.toMillis() > 0 && duration.toMinutes() < (5*60*1000) ) { 
    			return true;
    		}
		} 
		return false;
	}

}
