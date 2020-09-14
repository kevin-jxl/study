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
 * 
 *
 * @author ztman code generator
 * @date 2020-08-26 10:02:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_tenant")
public class SysTenant extends Model<SysTenant> {
	private static final long serialVersionUID = 1L;

	/**
	 * 租户id
	 */
	@TableId
	private Integer tenantId;
	/**
	 * 租户名称
	 */
	private String tenantName;
	/**
	 * 租户编码
	 */
	private String tenantCode;
	/**
	 * 上级租户
	 */
	private String tenantParent;
	/**
	 * 所属区域id
	 */
	private Integer areaId;
	/**
	 * 所属（管理员，用户）
	 */
	private Integer admin;
	/**
	 * 联系方式
	 */
	private String adminPhone;
	/**
	 * 创建人
	 */
	private Integer createBy;
	/**
	 * 创建时间
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date createTime;
	/**
	 * 修改人
	 */
	private Integer updateBy;
	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 删除标识 0-删除，1-正常
	 */
	private String delFlag;
	/**
	 * 有效标识 1-正常 0-停用 2-锁定
	 */
	private String tenantFlag;
	private String remark;


  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.tenantId;
  }
}
