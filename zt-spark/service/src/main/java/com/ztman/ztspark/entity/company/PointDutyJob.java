package com.ztman.ztspark.entity.company;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
  import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 值班 点检任务
 *
 * @author spark code generator
 * @date 2020-09-08 19:07:37
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("point_duty_job")
public class PointDutyJob extends Model<PointDutyJob> {
	private static final long serialVersionUID = 1L;

	/**
	 * 单号
	 */
	@TableId
	private Integer id;
	/**
	 * 租户ID
	 */
	private String tenantId;
	private String dutyId;
	private String dutyName;
	/**
	 * 派遣人id
	 */
	private String assignUserId;
	/**
	 * 派遣人姓名
	 */
	private String assignUserName;
	/**
	 * 派遣时间
	 */
	private Date assignTime;
	/**
	 * 值班人员ID
	 */
	private Integer peopleId;
	/**
	 * 是否正常（0是 1否）
	 */
	private String isOn;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 设备名称
	 */
	private String equipmentName;
	/**
	 * 设备编号
	 */
	private String equipmentNum;
	private String delFlag;


  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
