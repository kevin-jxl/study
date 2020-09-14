package com.ztman.admin.api.dto;

import com.ztman.admin.api.entity.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色Dto
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends SysRole {
	/**
	 * 角色部门Id
	 */
	private Integer roleDeptId;

	/**
	 * 部门名称
	 */
	private String deptName;

	/**
	 * 角色岗位Id
	 */
	private Integer postId;


	/**
	 * 岗位名称
	 */
	private String postName;

	/**
	 * 角色人数总数
	 */
	private Integer userCount;
	private String createPeople;
}
