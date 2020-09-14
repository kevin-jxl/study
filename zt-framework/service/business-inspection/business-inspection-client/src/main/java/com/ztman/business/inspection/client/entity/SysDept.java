package com.ztman.business.inspection.client.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
  import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 部门
 *
 * @author zxx
 * @date 2020-08-30 01:58:13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dept")
public class SysDept extends Model<SysDept> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer deptId;
	/**
	 * 部门名称
	 */
	private String name;
	/**
	 * 组织简称
	 */
	private String deptSimplify;
	/**
	 * 组织负责人
	 */
	private String deptLeader;
	/**
	 * 组织类别 0：区域 1：公司  2：部门
	 */
	private String deptType;
	/**
	 * 
	 */
	private Integer parentId;
	/**
	 * 排序
	 */
	private Integer orderNum;
	/**
	 * 0-删除，1-正常
	 */
	private String delFlag;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 修改人
	 */
	private String updateBy;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 工程师备注
	 */
	private String adminRemark;

  /**
   * 主键值
   */
  @Override
  protected Serializable pkVal() {
    return this.deptId;
  }
}
