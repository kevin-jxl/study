package com.ztman.common.security.util;


import cn.hutool.core.util.StrUtil;
import com.ztman.common.core.constant.CommonConstant;
import com.ztman.common.core.constant.SecurityConstants;
import com.ztman.common.security.DTO.SecruityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 安全工具类
 */
public class SecurityUtils {


	/**
	 * 获取Authentication
	 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取用户
	 */
	public static String getUser(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal == null) {
			return null;
		}
		return (String) principal;
	}

	public static String getClientId() {
		Authentication authentication = getAuthentication();
		if (authentication instanceof OAuth2Authentication) {
			OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
			return auth2Authentication.getOAuth2Request().getClientId();
		}
		return null;
	}

	/**
	 * 获取用户名字
	 */
	public static String getUser() {
		Authentication authentication = getAuthentication();
		return authentication.getName();
//		if (authentication == null) {
//			return null;
//		}
//		return getUser(authentication);
	}
	/**
	 * 获取用户信息，包含用户ID、部门ID
	 */
	public static SecruityUser getUserInfo(){
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities  = authentication.getAuthorities();
		SecruityUser secruityUser =new SecruityUser();
		authorities.stream().forEach(granted  ->{
			if( granted.getAuthority().startsWith("userId:")){
				secruityUser.setId(Integer.valueOf(granted.getAuthority().split(":")[1]));
			}else if ( granted.getAuthority().startsWith("userName:")){
				secruityUser.setUserName(granted.getAuthority().split(":")[1]);
			}else if ( granted.getAuthority().startsWith("userRealName:")){
				secruityUser.setRealName(granted.getAuthority().split(":")[1]);
			}else if ( granted.getAuthority().startsWith("userDeptId:")){
				secruityUser.setDeptId(granted.getAuthority().split(":")[1]);
			}else if ( granted.getAuthority().startsWith("userEmpNo:")){
				secruityUser.setEmpNo(granted.getAuthority().split(":")[1]);
			}else if ( granted.getAuthority().startsWith("companyId:")){
				secruityUser.setCompanyId(granted.getAuthority().split(":")[1]);
			}else if ( granted.getAuthority().startsWith("tenantFlag:")){
				secruityUser.setCompanyId(granted.getAuthority().split(":")[1]);
			}else if ( granted.getAuthority().startsWith("tenantId:")){
				secruityUser.setTenantId(granted.getAuthority().split(":")[1]);
			}
		}
		);
		Object principal = authentication.getPrincipal();

		return secruityUser;
		//return new SecruityUser(Integer.valueOf(userS[0].split(":")[1]),users);
		/*		if (principal instanceof sparkUser) {
			return (sparkUser) principal;
		}
		return null;*/
	}

	/**
	 * 获取用户角色信息
	 *
	 * @return 角色集合
	 */
	public static List<String> getRoles() {
		Authentication authentication = getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		List<String> roles = new ArrayList<>();
		authorities.stream()
			.filter(granted -> StrUtil.startWith(granted.getAuthority(), SecurityConstants.ROLE))
			.forEach(granted -> roles.add(StrUtil.removePrefix(granted.getAuthority(), SecurityConstants.ROLE)));
		return roles;
	}

	public static boolean checkRoles() {

		return getRoles().contains(CommonConstant.ROLE_ADMIN);
	}
}