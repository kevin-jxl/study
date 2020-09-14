package com.ztman.business.inspection.service.impl;
 
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import com.baomidou.mybatisplus.service.impl.ServiceImpl; 
 
import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
 
import com.ztman.business.inspection.mapper.SysCompanyMapper;
import com.ztman.business.inspection.mapper.SysTenantMapper;
import com.ztman.business.inspection.mapper.SysUserActivateMapper;
import com.ztman.business.inspection.mapper.SysUserMapper;
import com.ztman.business.inspection.client.dto.UserInfoDTO;
import com.ztman.business.inspection.client.dto.UserInitInfoDTO;
import com.ztman.business.inspection.client.entity.SysCompany; 
import com.ztman.business.inspection.client.entity.SysUser;
import com.ztman.business.inspection.client.entity.SysUserActivate;
import com.ztman.business.inspection.service.SysUserService;

@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysUserActivateMapper sysUserActivateMapper;
	@Autowired
	private SysTenantMapper sysTenantMapper;
	@Autowired
	private SysCompanyMapper  sysCompanyMapper;
	 

	@Override
	public Boolean isExist(String phone) {
		// TODO Auto-generated method stub
		return null != userInfoBy(phone);
	}
	@Override
	public SysUser userInfoBy(String phone) {
		// TODO Auto-generated method stub
		SysUser sysUser = new SysUser();
		sysUser.setDelFlag("1");
		sysUser.setPhone(phone);

		return sysUserMapper.selectOne(sysUser);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserInfoDTO activate(UserInitInfoDTO userInitInfoDTO) {
		// TODO Auto-generated method stub
		UserInfoDTO userInfoDTO = null;

		SysUser sysUser = userInfoBy(userInitInfoDTO.getPhone());

		if (null != sysUser) {
			SysUserActivate sysUserActivate = new SysUserActivate();
			sysUserActivate.setType("1");
			sysUserActivate.setTenantId(getTenantIdByUserId(sysUser.getUserId()));
			sysUserActivate.setUserId(sysUser.getUserId() + ""); 
			System.out.print("getTenantId::"+sysUserActivate.getTenantId());
			if (null ==  sysUserActivateMapper.selectOne(sysUserActivate)) {
				sysUserActivateMapper.insertSysUserActivate(sysUserActivate);

				sysUser.setCompanyId(userInitInfoDTO.getCompanyId());
				sysUser.setPassword(SecureUtil.md5(Base64.decodeStr(userInitInfoDTO.getPassword())));
				sysUser.setEmail(userInitInfoDTO.getEmail());
				sysUser.setPostName(userInitInfoDTO.getPostName());
				sysUser.setDeptId(userInitInfoDTO.getDeptId());
				sysUser.setWxOpenid(userInitInfoDTO.getWxOpenId());
				sysUser.setAvatar(userInitInfoDTO.getAvatar());
				sysUserMapper.updateSysUser(sysUser);

				userInfoDTO = loadUserInfoDTO(sysUser);
				SysCompany sysCompany = sysCompanyMapper.selectSysCompanyById(sysUser.getCompanyId());
				userInfoDTO.setCompanyName(sysCompany.getName());
				
			}

		}
		return userInfoDTO;
	}

	public String getTenantIdByUserId(Integer userId) {
		// TODO Auto-generated method stub

		return ((int)(new Random().nextDouble()*10000))+""+userId;
	}

	@Override
	public UserInitInfoDTO userInitInfoBy(String phone) {
		// TODO Auto-generated method stub
		UserInitInfoDTO userInitInfoDTO = null;

		SysUser sysUser = new SysUser();
		sysUser.setPhone(phone);
		sysUser.setDelFlag("1");
		sysUser = sysUserMapper.selectOne(sysUser);

		if (null != sysUser) {
			SysUserActivate sysUserActivate = new SysUserActivate();
			sysUserActivate.setUserId(sysUser.getUserId() + "");
			sysUserActivate = sysUserActivateMapper.selectOne(sysUserActivate);

			if (null == sysUserActivate) {
				userInitInfoDTO = new UserInitInfoDTO();
				userInitInfoDTO.setCompanyId(sysUser.getCompanyId());
				userInitInfoDTO.setDeptId(sysUser.getDeptId());
				userInitInfoDTO.setEmail(sysUser.getEmail());
				userInitInfoDTO.setPassword("");
				userInitInfoDTO.setPhone(phone);
				userInitInfoDTO.setPostName(sysUser.getPostName());
				userInitInfoDTO.setAvatar(sysUser.getAvatar());
				userInitInfoDTO.setRealName(sysUser.getUserRealname());
			}
		}

		return userInitInfoDTO;
	}

	@Override
	public UserInfoDTO userInfoBy(int userId) {
		// TODO Auto-generated method stub
		UserInfoDTO userInfoDTO = null;
		SysUser sysUser = sysUserMapper.selectSysUserById(userId);
		if (null != sysUser) {
			userInfoDTO = loadUserInfoDTO(sysUser);
			SysCompany sysCompany = sysCompanyMapper.selectSysCompanyById(sysUser.getCompanyId());
			userInfoDTO.setCompanyName(sysCompany.getName());
			
		}

		return userInfoDTO;
	}

	@Override
	public SysUser userInfoBy(String phone, String password) {
		// TODO Auto-generated method stub
//		UserInfoDTO userInfoDTO = null;
		password = Base64.decodeStr(password);
		SysUser sysUser = userInfoBy(phone);
		if (null != sysUser) {
			if (!sysUser.getPassword().equals(SecureUtil.md5(password))) {
//				userInfoDTO = loadUserInfoDTO(sysUser);
				return null;
			}
		}
		return sysUser;
	}

	private UserInfoDTO loadUserInfoDTO(SysUser sysUser) {
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		userInfoDTO.setCompanyId(sysUser.getCompanyId());
		userInfoDTO.setDeptId(sysUser.getDeptId());
		userInfoDTO.setEmail(sysUser.getEmail());
		userInfoDTO.setPostName(sysUser.getPostName());
		userInfoDTO.setDeptName("");
		userInfoDTO.setCompanyName("");
		userInfoDTO.setTel(sysUser.getRemark());
		userInfoDTO.setPhone(sysUser.getPhone());
		userInfoDTO.setRealName(sysUser.getUserRealname());
		return userInfoDTO;
	}

	@Override
	public Boolean updatePasswordBy(int userId, String oldPwd, String newPwd) {
		// TODO Auto-generated method stub
		oldPwd = Base64.decodeStr(oldPwd);
		newPwd = Base64.decodeStr(newPwd);
		SysUser sysUser = sysUserMapper.selectSysUserById(userId);
		if (SecureUtil.md5(oldPwd).equals(sysUser.getPassword()) ) {
			sysUser.setPassword(SecureUtil.md5(newPwd));
			return sysUserMapper.updateById(sysUser) > 0;
		}
		return false;
	}

	@Override
	public Boolean updateUserInfoBy(int userId, UserInfoDTO userInfoDTO) {
		// TODO Auto-generated method stub
		SysUser sysUser = new SysUser();
		sysUser.setUserId(userId);
		sysUser.setEmail(userInfoDTO.getEmail());  
		sysUser.setAvatar(userInfoDTO.getAvatar());
		sysUser.setPostName(userInfoDTO.getPostName());
		sysUser.setCompanyId(userInfoDTO.getCompanyId());
		sysUser.setRemark(userInfoDTO.getTel());
//		sysUser.setTel(userInfoDTO.getTel());  
		 
		return sysUserMapper.updateById(sysUser)>0;
	}

}
