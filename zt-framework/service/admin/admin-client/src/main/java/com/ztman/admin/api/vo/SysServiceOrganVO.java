package com.ztman.admin.api.vo;

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
 * 业务机构表
 *
 * @author  code generator
 * @date 2020-08-25 19:44:47
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_service_organ")
public class SysServiceOrganVO extends Model<SysServiceOrganVO> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer serviceId;
	/**
	 * 业务机构管理
	 */
	private String serviceName;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 行政机构(车场)id
	 */
	private Integer administrativeOrganId;
	/**
	 * 行政机构(车场)
	 */
	private String organName;
	/**
	 * 负责人
	 */
	private String adminName;
	/**
	 * 负责人联系方式
	 */
	private String phone;
	/**
	 * 创建人id
	 */
	private Integer createId;
	/**
	 * 创建人
	 */
	private String createName;
	/**
	 * 创建时间
	 */
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
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
	private String ext5;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.serviceId;
  }
}
