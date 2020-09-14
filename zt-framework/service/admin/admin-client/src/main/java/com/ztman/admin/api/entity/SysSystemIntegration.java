package com.ztman.admin.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 1、系统集成、2、物联网集成
 *
 * @author ztman code generator
 * @date 2020-09-07 16:06:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_system_integration")
public class SysSystemIntegration extends Model<SysSystemIntegration> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer systemId;
	/**
	 * 系统名称
	 */
	private String systemName;
	/**
	 * 地址
	 */
	private String systemHttp;
	/**
	 * 管理单位
	 */
	private String systemUnit;
	/**
	 * 对接人
	 */
	private String systemMeetPeople;
	/**
	 * 联系电话
	 */
	private String systemMeetPeoplePhone;
	/**
	 * 
	 */
	private Integer createBy;
	/**
	 * 
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date createTime;
	/**
	 * 
	 */
	private Integer updateBy;
	/**
	 * 
	 */
	private Date updateTime;
	/**
	 * 状态 1 有效 0 异常
	 */
	private String systemFlag;
	/**
	 * 
	 */
	private String delFlag;
	/**
	 * 1系统集成，2物联网集成
	 */
	private String systemStatus;
	private String remark;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.systemId;
  }
}
