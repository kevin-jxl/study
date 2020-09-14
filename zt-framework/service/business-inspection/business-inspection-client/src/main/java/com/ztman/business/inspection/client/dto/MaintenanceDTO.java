package com.ztman.business.inspection.client.dto;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class MaintenanceDTO {
	/**
	 * 工单号
	 */
	private String workOrderId;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 维修完成时间
	 */
	private LocalDateTime completeDatetime;
	/**
	 * 附件组ID
	 */
	private String attachmentGroupId;
	/**
	 * 备件
	 */
	private String part;
}
