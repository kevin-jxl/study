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
 * 值班信息管理表
 *
 * @author spark code generator
 * @date 2020-09-03 16:46:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("company_duty")
public class CompanyDuty extends Model<CompanyDuty> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 值班班次
	 */
	private String dutyType;
	/**
	 * 负责车场
	 */
	private Integer organId;
	/**
	 * 开始时间
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startTime;
	/**
	 * 结束时间
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endTime;

	private Integer comId;
	/**
	 * 值班状态
	 */
	private String dutyStatus;
	/**
	 * 创建时间
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date createTime;
	/**
	 * 创建人
	 */
	private Integer createBy;
	/**
	 * 修改时间
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date updateTime;
	/**
	 * 修改人
	 */
	private Integer updateBy;
	/**
	 * 删除状态，0-删除，1-正常
	 */
	private String delFlag;

	private String taskFlag;
	private String taskDescription;


  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
