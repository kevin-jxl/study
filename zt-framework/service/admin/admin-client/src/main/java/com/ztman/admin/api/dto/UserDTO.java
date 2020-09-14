package com.ztman.admin.api.dto;

import com.ztman.admin.api.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends SysUser {
	/**
	 * 角色ID
	 */

	private List<Integer> posts;
	private Integer roleId;
	/**
	 * 新密码
	 */
	private String newpassword1;

	/**
	 * 密保工具(邮箱或者手机号)
	 */
	private String encrypted;
}
