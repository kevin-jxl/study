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
 * 系统集成的接口管理
 *
 * @author ztman code generator
 * @date 2020-09-07 16:06:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_system_interface")
public class SysSystemInterface extends Model<SysSystemInterface> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer interfaceId;
	/**
	 * 系统id
	 */
	private Integer sysId;
	/**
	 * 接口名
	 */
	private String interfaceName;
	/**
	 * URL
	 */
	private String url;
	/**
	 * 是否启用 1 启用 0 锁定
	 */
	private String interfaceFlag;
	/**
	 * 接口状态，1 正常 0 异常
	 */
	private String status;
	/**
	 * 最后检测时间
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date lastTestTime;
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
	 * 
	 */
	private String delFlag;
	private String remark;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.interfaceId;
  }
}
