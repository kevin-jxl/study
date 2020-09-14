package com.ztman.admin.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
  import java.io.Serializable;

/**
 * 用户关联信息表
 *
 * @author  code generator
 * @date 2020-08-24 09:14:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_details")
public class SysUserDetails extends Model<SysUserDetails> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer userId;
	/**
	 * 行政区域id
	 */
	private Integer areaId;
	/**
	 * 
	 */
	private Integer organId;
	/**
	 * 
	 */
	private Integer companyId;
	/**
	 * 职务名称（不关联，手动输入类型）
	 */
	private String postName;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.userId;
  }
}
