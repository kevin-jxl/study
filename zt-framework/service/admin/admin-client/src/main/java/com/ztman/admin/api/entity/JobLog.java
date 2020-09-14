package com.ztman.admin.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
  import java.io.Serializable;

/**
 * 定时任务执行历史
 *
 * @author  code generator
 * @date 2018-11-13 18:38:43
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("job_log")
public class JobLog extends Model<JobLog> {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@TableId
	private Long id;
	/**
	 * 任务执行类
	 */
	private String jobName;
	/**
	 * 任务名称
	 */
	private String description;
	/**
	 * 执行时长
	 */
	private String jobExeTime;
	/**
	 * 执行结果
	 */
	private Integer jobExeResult;
	/**
	 * 执行开始时间
	 */
	private String jobExeBegintime;
	/**
	 * 执行结束时间
	 */
	private String jobExeEndtime;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
