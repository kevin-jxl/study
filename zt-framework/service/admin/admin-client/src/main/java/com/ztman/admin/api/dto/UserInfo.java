package com.ztman.admin.api.dto;

import com.ztman.admin.api.entity.SysUser;

import java.io.Serializable;

/**
 * <p>
 * commit('SET_ROLES', data)
 * commit('SET_NAME', data)
 * commit('SET_AVATAR', data)
 * commit('SET_INTRODUCTION', data)
 * commit('SET_PERMISSIONS', data)
 */
public class UserInfo implements Serializable {
	/**
	 * 用户基本信息
	 */
	private SysUser sysUser;
	/**
	 * 权限标识集合
	 */
	private String[] permissions;

	/**
	 * 角色集合
	 */
	private String[] roles;
	private String tenantIds;
	private String cpmpanyId;
	private String organId;

	public String getCpmpanyId() {
		return cpmpanyId;
	}

	public void setCpmpanyId(String cpmpanyId) {
		this.cpmpanyId = cpmpanyId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getTenantIds() {
		return tenantIds;
	}

	public void setTenantIds(String tenantIds) {
		this.tenantIds = tenantIds;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public String[] getPermissions() {
		return permissions;
	}

	public void setPermissions(String[] permissions) {
		this.permissions = permissions;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}
}
