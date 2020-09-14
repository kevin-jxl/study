package com.ztman.business.inspection.client.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
  import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工单过程表
 *
 * @author zxx
 * @date 2020-08-29 10:34:49
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("work_order_process")
public class WorkOrderProcess extends Model<WorkOrderProcess> {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Long id;
	/**
	 * 工单号
	 */
	private String workOrderId;
	/**
	 * 过程标识（1.已派遣 2.已受理 3.处置中 4.已完成 5.已关闭）
	 */
	private String tag;
	/**
	 * 过程标识名称（1.已派遣 2.已受理 3.处置中 4.已完成 5.已关闭）
	 */
	private String tagName;
	/**
	 * 该过程参与人姓名
	 */
	private String joinName;
	/**
	 * 该过程参与人用户id
	 */
	private String joinUserId;
	/**
	 * 该过程操作时间
	 */
	private LocalDateTime operationTime;
	/**
	 * 备注
	 */
	private String note;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
