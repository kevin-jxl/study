package com.ztman.admin.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
  import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 *
 * @author ztman code generator
 * @date 2019-02-22 14:43:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_power_label")
public class SysPowerLabel extends Model<SysPowerLabel> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 所属菜单ID
	 */
	private Integer menuId;
	/**
	 * 权限名称
	 */
	private String powerName;
	/**
	 * 涉及的权限表
	 */
	private String powerTable;
	/**
	 * 权限字段
	 */
	private String powerFiled;
	/**
	 * 权限值
	 */
	private String powerValue;
	/**
	 * 权限控制类型 0：等于 1：不等于 2：包含 3：不包含 4:大于 5：大于等于 6：小于 7：小于等于
	 */
	private String powerControl;
	/**
	 * 权限状态 0：有效 1：无效
	 */
	private String powerFlag;
	/**
	 * 权限描述
	 */
	private String powerDesc;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 删除标志 0：正常 1：删除
	 */
	private String delFlag;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
