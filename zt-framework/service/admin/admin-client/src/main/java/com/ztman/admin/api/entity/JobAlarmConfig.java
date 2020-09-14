package com.ztman.admin.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
  import java.io.Serializable;

/**
 * 任务告警配置
 *
 * @author  code generator
 * @date 2018-11-13 17:52:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("job_alarm_config")
public class JobAlarmConfig extends Model<JobAlarmConfig> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 任务执行类名
	 */
	private String jobName;
	/**
	 * 任务分组
	 */
	private String jobGroup;
	/**
	 * 是否告警0：告警 1：不告警
	 */
	private String isAlarm;
	/**
	 * 告警电话
	 */
	private String alarmPhones;
	/**
	 * 告警邮箱
	 */
	private String alarmMails;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
