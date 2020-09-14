package com.ztman.business.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import com.ztman.business.inspection.client.dto.UserInitInfoDTO;
import com.ztman.business.inspection.client.dto.UserInfoDTO;
import com.ztman.business.inspection.client.entity.SysUser;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.common.core.util.Query;
/**
 * 系统用户表 服务接口层
 *
 * @author zxx
 * @date 2020-08-29 10:28:55
 */
public interface SysUserService extends IService<SysUser> {
      

	public Boolean isExist(String phone);

	public UserInfoDTO activate(UserInitInfoDTO userInitInfoDTO);

	public UserInitInfoDTO userInitInfoBy(String phone);

	public UserInfoDTO userInfoBy(int userId);

	public SysUser userInfoBy(String phone);
	
	public SysUser userInfoBy(String phone, String password);

	public Boolean updatePasswordBy(int userId, String oldPwd, String newPwd);

	public Boolean updateUserInfoBy(int userId, UserInfoDTO userInfoDTO);

}

