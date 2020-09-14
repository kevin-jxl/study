package com.ztman.business.inspection.client.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
  import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 随机点检
 *
 * @author zxx
 * @date 2020-08-30 01:56:43
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("casual_point_inspection")
public class CasualPoininspection extends Model<CasualPoininspection> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
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

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
