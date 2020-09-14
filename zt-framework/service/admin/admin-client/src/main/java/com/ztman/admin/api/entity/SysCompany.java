package com.ztman.admin.api.entity;

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
 * 企业表
 *
 * @author jxl
 * @date 2020-08-21 10:04:46
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_company")
public class SysCompany extends Model<SysCompany> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer comId;
	/**
	 * 企业名称
	 */
	private String name;
	/**
	 * 企业编号
	 */
	private String code;
	/**
	 * 所属租户id
	 */
//	private Integer theirId;
	/**
	 * 负责人id
	 */
	private String adminName;
	/**
	 * 联系方式
	 */
	private String phone;
	/**
	 * 创建人id
	 */
	private Integer createId;
	/**
	 * 创建时间
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date createTime;
	/**
	 * 服务状态
	 */
	private String status;

	private String delFlag;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 
	 */
	private String ext1;
	/**
	 * 
	 */
	private String ext2;
	/**
	 * 
	 */
	private String ext3;
	/**
	 * 
	 */
	private String ext4;
	/**
	 * 
	 */
	private String tenantId;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.comId;
  }
}
