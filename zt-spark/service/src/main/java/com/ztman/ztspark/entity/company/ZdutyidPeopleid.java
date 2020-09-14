package com.ztman.ztspark.entity.company;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
  import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 值班人员关联表
 *
 * @author spark code generator
 * @date 2020-09-08 19:20:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("zt_dutyid_peopleid")
public class ZdutyidPeopleid extends Model<ZdutyidPeopleid> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer dutyPeopleId;
	/**
	 * 值班id
	 */
	private Integer dutyId;
	/**
	 * 人员id
	 */
	private Integer peopleId;
	/**
	 * 值班开始时间
	 */
	private String dutyStartTime;
	/**
	 * 值班结束时间
	 */
	private String dutyEndTime;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.dutyPeopleId;
  }
}
