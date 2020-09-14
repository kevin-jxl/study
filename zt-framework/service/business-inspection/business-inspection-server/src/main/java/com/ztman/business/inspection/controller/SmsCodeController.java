package com.ztman.business.inspection.controller;


import com.ztman.business.inspection.client.entity.SmsCode;
import com.ztman.business.inspection.service.SmsCodeService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.plugins.Page; 
import com.ztman.common.core.util.ExcelUtil;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import com.ztman.common.security.DTO.SecruityUser; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ztman.common.security.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletResponse; 
 

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Ref;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 短信验证码
 *
 * @author zxx
 * @date 2020-08-29 11:11:37
 */
@RestController
@RequestMapping("/smscode")
@Slf4j
public class SmsCodeController {
    @Autowired
    private SmsCodeService smsCodeService;  

     

    /**
     * 发送验证码
     * @param phone
     * @return R
     * @throws Exception 
     */
    @GetMapping("/send")
    @Transactional(rollbackFor = UnsupportedEncodingException.class)
    public R send(@RequestParam("phone") String phone,@RequestParam("type") String type) throws Exception {
    	
        //从服务端获取验证码 
    	String code = smsCodeService.getCodeBy(phone,type);
    	
    	if(null != code) {
    		
    		//发送验证码
    		SecruityUser user = SecurityUtils.getUserInfo();
    		String url = ""; 
    		try {
    			url = "http://101.201.54.221:8888/sms.aspx?action=send&userid=15&account=icontech&password=ICON987tech654&mobile="+phone+"&content="+
    		URLEncoder.encode("【巡检通】验证码："+code+"，您正在请求巡检通注册，请在5分钟内使用，如非本人请忽略本短信。", "UTF-8").toString()+"&sendTime=&extno=";
				 
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		HttpRequest req =  new HttpRequest(url);
    		HttpResponse res = req.execute();  
    	}
        return new R<>(true);
    } 
    
     
    /**
     * 验证
     * @param phone
     * @param type
     * @param code
     * @return R
     */
//    @GetMapping("/validate")
//    public R validate(@RequestParam("phone") String phone,@RequestParam("type") String type,@RequestParam("code") String code) throws Exception {
//    
//        return new R<>(smsCodeService.validate(phone,type,code));
//    }
//     
}
