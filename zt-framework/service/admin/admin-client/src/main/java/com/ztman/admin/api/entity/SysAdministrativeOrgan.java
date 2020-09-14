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
 * 行政机构表
 *
 * @author jxl
 * @date 2020-08-21 15:37:10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_administrative_organ")
public class SysAdministrativeOrgan extends Model<SysAdministrativeOrgan> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer organId;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 负责人
	 */
	private String adminname;
	private String role;
	/**
	 * 负责人联系方式
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
	 * 备注
	 */
	private String remark;
	/**
	 * 区域id
	 */
	private String delFlag;
	/**
	 * 所属公司
	 */
	private Integer comId;
	/**
	 * 所属公司名称
	 */
	private String companyId;

	private String comName;
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
	private String ext5;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.organId;
  }
}
