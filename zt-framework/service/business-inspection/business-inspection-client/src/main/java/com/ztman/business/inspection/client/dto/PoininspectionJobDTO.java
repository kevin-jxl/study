package com.ztman.business.inspection.client.dto;


import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
  import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 点检任务
 *
 * @author zxx
 * @date 2020-08-29 11:00:39
 */
@Data  
public class PoininspectionJobDTO  {
	private static final long serialVersionUID = 1L;

	/**
	 * 单号
	 */ 
	private String id;
	/**
	 * 租户ID
	 */
	private String tenantId;
	/**
	 * 派遣人ID
	 */
	private String assignUserId;
	/**
	 * 派遣人姓名
	 */
	private String assignUserName;
	/**
	 * 派遣时间
	 */
	private LocalDateTime assignTime;
	/**
	 * 点检人ID
	 */
	private String doUserId;
	/**
	 * 点检人姓名
	 */
	private String doUserName;
	/**
	 * 点检时间
	 */
	private LocalDateTime doTime;
	/**
	 * 点检描述
	 */
	private String description;
	/**
	 * 是否正常（0是 1否）
	 */
	private String isOn;
	/**
	 * 附件组ID
	 */
	private String attachmentGroupId;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 问题类型
	 */
	private String problemType;
	/**
	 * 地点
	 */
	private String address;
	/**
	 * 设备名称
	 */
	private String equipmentName;
	/**
	 * 设备编号
	 */
	private String equipmentId;
   
}
