package com.ztman.admin.api.vo;

import com.ztman.admin.api.entity.SysConsumer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户管理
 *
 * @author shuda
 * @date 2020-08-21 16:21:27
 */
@Data
public class SysConsumerVO extends SysConsumer implements Serializable {
	private static final long serialVersionUID = 1L;



	/**
	 * 企业名称
	 */
	private String companyName;
	/**
	 * 车场名称
	 */
	private String organName;

	/**
	 * 企业id数组
	 */
	private List companyIds;
	private String roleName;
	private String roleCode;
	private Integer roleId;
	private LocalDateTime lastLoginDate;




}
