package com.ztman.auth.service;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.ztman.admin.api.dto.UserInfo;
import com.ztman.admin.api.entity.SysUser;
import com.ztman.admin.api.feign.RemoteUserService;
import com.ztman.common.core.constant.CommonConstant;
import com.ztman.common.core.constant.SecurityConstants;
import com.ztman.common.core.util.R;
import com.ztman.common.security.DTO.SparkUser;
import com.ztman.common.security.util.SparkUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户详细信息
 *
 * @author ztman
 */
@Slf4j
@Service
@AllArgsConstructor
public class SparkUserDetailsServiceImpl implements SparkUserDetailsService {
	private final RemoteUserService remoteUserService;

	/**
	 * 用户密码登录
	 *
	 * @param username 用户名
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		R<UserInfo> result = remoteUserService.info(username, SecurityConstants.FROM_IN);
		return getUserDetails(result);
	}


	/**
	 * 根据社交登录code 登录
	 *
	 * @param inStr TYPE@CODE
	 * @return UserDetails
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserBySocial(String inStr) throws UsernameNotFoundException {
		return getUserDetails(remoteUserService.social(inStr));
	}

	/**
	 * 构建userdetails
	 *
	 * @param result 用户信息
	 * @return
	 */
	private UserDetails getUserDetails(R<UserInfo> result) {
		if (result == null || result.getData() == null) {
			throw new UsernameNotFoundException("用户不存在");
		}

		UserInfo info = result.getData();
		Set<String> dbAuthsSet = new HashSet<>();
		if (ArrayUtil.isNotEmpty(info.getRoles())) {
			// 获取角色
			Arrays.stream(info.getRoles()).forEach(role -> dbAuthsSet.add(SecurityConstants.ROLE + role));
			// 获取资源
			dbAuthsSet.addAll(Arrays.asList(info.getPermissions()));

		}
		SysUser user = info.getSysUser();
		//将用户ID填入 资源
		dbAuthsSet.add("userId:"+ user.getUserId());
		dbAuthsSet.add("userName:"+ user.getUsername());
		dbAuthsSet.add("userRealName:"+ user.getUserRealname());
		dbAuthsSet.add("userDeptId:"+ user.getDeptId());
		dbAuthsSet.add("userEmpNo:"+ user.getEmpno());
		dbAuthsSet.add("companyId:"+ user.getCompanyId());
		dbAuthsSet.add("tenantFlag:"+ user.getTenantFlag());
		dbAuthsSet.add("tenantId:"+ user.getTenantId());
		Collection<? extends GrantedAuthority> authorities
			= AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));
		boolean enabled = StrUtil.equals(user.getDelFlag(), CommonConstant.STATUS_NORMAL);
		boolean accountNonLocked = StrUtil.equals(user.getUserFlag(), CommonConstant.STATUS_NORMAL);
		// 构造security用户
//		return new User(info.getSysUser().getUsername(), SecurityConstants.MD5+ user.getPassword().toLowerCase(), enabled,
//				true, true, true, authorities);

		return new SparkUser(user.getUserId(), user.getDeptId(), user.getUsername(), SecurityConstants.MD5 + user.getPassword().toLowerCase(), enabled,
				true, true, accountNonLocked, authorities);
	}

}
