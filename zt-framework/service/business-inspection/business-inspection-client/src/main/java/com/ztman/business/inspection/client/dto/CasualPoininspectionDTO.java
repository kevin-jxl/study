package com.ztman.business.inspection.client.dto;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotations.TableId;

import lombok.Data;

@Data
public class CasualPoininspectionDTO {
	/**
	 * id
	 */ 
	private String id;
	/**
	 * 租户ID
	 */
	private String tenantId;
	/**
	 * 检查人ID
	 */
	private String doUserId;
	/**
	 * 检查人姓名
	 */
	private String doUserName;
	/**
	 * 点检状态（0正常 1异常）
	 */
	private String status;
	/**
	 * 设备名称
	 */
	private String equipmentName;
	/**
	 * 设备编号
	 */
	private String equipmentId;
	/**
	 * 地点
	 */
	private String address;
	/**
	 * 附件组ID
	 */
	private String groupId;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
}
