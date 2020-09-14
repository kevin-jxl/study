package com.ztman.business.inspection.client.dto;

import org.springframework.web.bind.annotation.RequestParam;

import lombok.Data;

@Data
public class UserInitInfoDTO {
	 
		/**
		 * 登陆密码
		 */
		private String password; 
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
		 * 职位id
		 */
		private Integer postId; 
		/**
		 * 职位id
		 */
		private String postName;  
		/**
		 * 企业Id
		 */
		private Integer companyId; 
		
		/**
		 *微信openid
		 */
		private String wxOpenId; 
		/**
		 *头像
		 */
		private String avatar; 
		/**
		 *姓名
		 */
		private String realName; 
		
		
		 
}
