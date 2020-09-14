package com.ztman.business.inspection.controller;

 
import com.ztman.business.inspection.client.dto.UserInitInfoDTO;
import com.ztman.business.inspection.client.dto.UserInfoDTO;
import com.ztman.business.inspection.client.entity.SysUser;
import com.ztman.business.inspection.client.vo.UserDetailsVo;
import com.ztman.business.inspection.service.SmsCodeService;
import com.ztman.business.inspection.service.SysCompanyService;
import com.ztman.business.inspection.service.SysUserActivateService;
import com.ztman.business.inspection.service.SysUserService;
import com.ztman.business.inspection.service.impl.UserDetailsImpl;
import com.ztman.business.inspection.util.JwtUtil;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.plugins.Page; 
import com.ztman.common.core.util.ExcelUtil;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import com.ztman.common.security.DTO.SecruityUser;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; 
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 系统用户表
 *
 * @author zxx
 * @date 2020-08-29 10:28:55
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class SysUserController {
    @Autowired
    private SysUserService sysUserService; 
    @Autowired
    private SmsCodeService smsCodeService;
    
    @Autowired
    private UserDetailsImpl userDetailsImpl;
    @Autowired
    private SysCompanyService sysCompanyService;
    
    
     
    //手机号和验证码获取用户初始化信息
    @GetMapping("/initInfo")
    public R userInitInfo(@RequestParam() String phone,@RequestParam() String code) {
    	
    	if(!smsCodeService.validate(phone, "1", code)) {
    		return new R<>(new Throwable("验证码无效"));
    	}
    	
    	UserInitInfoDTO userInfoDTO = sysUserService.userInitInfoBy(phone);
    	if (null ==  userInfoDTO) {
    		return new R<>(new Throwable("未找到该手机号的用户信息，请联系管理员"));
		}
    	
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("userInfo", userInfoDTO);
    	map.put("companys", sysCompanyService.withList(sysUserService.userInfoBy(phone).getUserId()));
        return new R<>(map);
    }
    
    //用户注册（激活）
    @PostMapping("/initComplete")
    public R register(@RequestBody() UserInitInfoDTO userInitInfoDTO) {
 
    	//提交用户信息并激活 
    	UserInfoDTO userInfoDTO = sysUserService.activate(userInitInfoDTO);
    	if(null == userInfoDTO) {
    		return new R<>(new Throwable("提交失败"));
    	}
        return new R<>(userInfoDTO);
    }
    
    //获取户信息（登录后可使用） 
    @GetMapping("/info")
    public R userInfo() { 
    	System.out.print("userDetailsVo.getId()::"+userDetailsImpl.getUserDetail().getId());
    	UserInfoDTO userInfoDTO = sysUserService.userInfoBy(userDetailsImpl.getUserDetail().getId());
    	if (null ==  userInfoDTO) {
    		return new R<>(new Throwable("未查询到用户信息"));
		}
        return new R<>(userInfoDTO);
    }
    //登录 
    @PostMapping("/login")
    public R login(@RequestParam() String phone,@RequestParam() String password) {
    	
    	SysUser sysUser = sysUserService.userInfoBy(phone,password);
    	if (null ==  sysUser) {
    		return new R<>(new Throwable("用户名或密码不正确"));
		} 
    	UserDetailsVo user = new UserDetailsVo(sysUser.getUserId(), sysUser.getPhone(), sysUser.getUserRealname(), sysUser.getPassword()); 
    	
        return new R<>(JwtUtil.createToken(user));
    }
    
    //修改用户信息 
    @PostMapping("/update")
    public R update(@RequestBody() UserInfoDTO userInfoDTO) { 
    	Boolean flg = sysUserService.updateUserInfoBy(userDetailsImpl.getUserDetail().getId(),userInfoDTO);
    	if (!flg) {
    		return new R<>(new Throwable("修改失败"));
		}
        return new R<>();
    }
    //修改密码 
    @PostMapping("/updatePwd")
    public R updatePwd(@RequestParam("oldPwd") String oldPwd,@RequestParam("newPwd") String newPwd) { 
    	Boolean flg = sysUserService.updatePasswordBy(userDetailsImpl.getUserDetail().getId(),oldPwd,newPwd);
    	if (!flg) {
    		return new R<>(new Throwable("旧密码错误"));
		}
    	//强制下线
    	userDetailsImpl.removeToken();
        return new R<>(null,"已强制下线，请重新登录");
    }
     
 
}
