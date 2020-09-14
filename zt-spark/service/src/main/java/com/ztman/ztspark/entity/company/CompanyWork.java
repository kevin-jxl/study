package com.ztman.ztspark.entity.company;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 考勤管理表
 *
 * @author spark code generator
 * @date 2020-09-03 19:07:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("company_work")
public class CompanyWork extends Model<CompanyWork> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer workId;
	/**
	 * 考勤开始时间
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date workStartTime;
	/**
	 * 考勤开始时间
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date workEndTime;
	/**
	 * 姓名
	 */
	private String workName;
	/**
	 * 部门
	 */
	private String workDepartment;

	private Integer comId;
	/**
	 * 职位
	 */
	private String workPost;
	/**
	 * 出勤
	 */
	private Integer workAttendance;
	/**
	 * 异常
	 */
	private Integer workAbnormal;
	/**
	 * 请假
	 */
	private Integer workLeave;
	/**
	 * 加班
	 */
	private Integer workOvertime;
	/**
	 * 调休
	 */
	private Integer workOff;
	/**
	 * 外出
	 */
	private Integer workOut;
	/**
	 * 删除状态，0-删除，1-正常
	 */
	private String delFlag;

	private String dayFlag;

	private String startTime;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.workId;
  }
}
