package com.ztman.admin.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
  import java.io.Serializable;

/**
 * 角色与部门、岗位对应关系
 *
 * @author  code generator
 * @date 2018-10-15 15:13:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role_dept_post")
public class SysRoleDeptPost extends Model<SysRoleDeptPost> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 角色ID
	 */
	private Integer roleId;
	/**
	 * 部门ID
	 */
	private Integer deptId;
	/**
	 * 岗位ID
	 */
	private Integer postId;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
