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
 * 人员管理表
 *
 * @author spark code generator
 * @date 2020-09-03 16:46:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("company_people")
public class CompanyPeople extends Model<CompanyPeople> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 所属公司
	 */
	private Integer company;
	/**
	 * 所属车场
	 */
	private Integer parkingLot;
	/**
	 * 岗位
	 */
	private Integer post;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 联系方式
	 */
	private String tel;
	/**
	 * 职位
	 */
	private Integer department;
	/**
	 * 备注
	 */
	private String remark;

	private Integer comId;

	private String companyId;
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
	private String createName;
	/**
	 * 修改时间
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date updateTime;

	private Date startTime;

	private Date endTime;
	/**
	 * 修改人
	 */
	private Integer updateBy;
	/**
	 * 删除状态，0-删除，1-正常
	 */
	private String delFlag;

	private String lng;

	private String lat;

	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date loginTime;

	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date lastLoginDate;

	private String code;

	private String isDuty;

	private String avatar;

	private String comName;

	private String organName;

	private String firstDay;

	private String lastDay;

	private String organId;

	private String serviceId;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
