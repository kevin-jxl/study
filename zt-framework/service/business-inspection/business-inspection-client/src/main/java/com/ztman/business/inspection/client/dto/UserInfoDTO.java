package com.ztman.business.inspection.client.dto;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotations.TableId;

import lombok.Data;
@Data
public class UserInfoDTO {
	private static final long serialVersionUID = 1L;
	 
	 
	  
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 固话
	 */
	private String tel;
	/**
	 * 邮箱
	 */
	private String email; 
	/**
	 * 部门ID
	 */
	private Integer deptId;  
	/**
	 * 部门名称
	 */
	private String deptName;
	/**
	 * 职位
	 */
	private String postId;
	/**
	 * 职位名称
	 */
	private String postName;  
	/**
	 * 企业Id
	 */
	private Integer companyId; 
	/**
	 * 企业名称
	 */
	private String companyName; 
	/**
	 * 头像
	 */
	private String avatar; 
	/**
	 *姓名
	 */
	private String realName; 
	 
	
}
