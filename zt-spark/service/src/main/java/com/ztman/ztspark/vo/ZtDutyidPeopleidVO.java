package com.ztman.ztspark.vo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ztman.ztspark.dto.PointDutyJobDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 值班人员关联表
 *
 * @author spark code generator
 * @date 2020-09-08 19:20:48
 */
@Data
public class ZtDutyidPeopleidVO extends Model<ZtDutyidPeopleidVO> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer dutyPeopleId;
	/**
	 * 人员id
	 */
	private Integer peopleId;
	/**
	 * 人员名称
	 */
	private String peopleName;
	/**
	 * 人员电话
	 */
	private String peoplePhone;
	/**
	 * 值班开始时间
	 */
	private String dutyStartTime;
	/**
	 * 值班结束时间
	 */
	private String dutyEndTime;
	/**
	 * 任务描述
	 */
	private String taskDescription;
	private String[] time;

	private List<PointDutyJobDTO> task;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.dutyPeopleId;
  }
}
