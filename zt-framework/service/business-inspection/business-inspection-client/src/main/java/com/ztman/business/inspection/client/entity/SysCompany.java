package com.ztman.business.inspection.client.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
  import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 企业
 *
 * @author zxx
 * @date 2020-08-30 01:59:00
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
	 * 所属租户
	 */
	private Integer tenantId;
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
	private LocalDateTime createTime;
	/**
	 * 服务状态
	 */
	private String status;
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
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.comId;
  }
}
