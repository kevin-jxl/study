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
 * 行政区域表
 *
 * @author jxl
 * @date 2020-08-21 15:36:59
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_administrative_area")
public class SysAdministrativeArea extends Model<SysAdministrativeArea> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer areaId;
	/**
	 * 名称
	 */
	private String areaName;
	/**
	 * 父级id
	 */
	private Integer parentId;
	/**
	 * 区划代码
	 */
	private String code;
	/**
	 * 负责人
	 */
	private String adminname;
	/**
	 * 负责人电话
	 */
	private String phone;
	/**
	 * 创建人id
	 */
	private Integer createId;

	private String createName;
	/**
	 * 创建时间
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date createTime;
	/**
	 * 备注
	 */
	private String remark;
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
	private String delFlag;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.areaId;
  }
}
