package com.ztman.business.inspection.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztman.business.inspection.client.vo.UserDetailsVo;
import com.ztman.business.inspection.util.JwtUtil;

@Service
public class UserDetailsImpl {
	@Autowired
    private HttpServletRequest request;
	
	public UserDetailsVo getUserDetail() {
		return  new UserDetailsVo(Integer.parseInt(request.getAttribute("id").toString()), request.getAttribute("userName").toString(), request.getAttribute("realName").toString(),
				"");
	}

	public void removeToken() {
		// TODO Auto-generated method stub
		
	}
}
