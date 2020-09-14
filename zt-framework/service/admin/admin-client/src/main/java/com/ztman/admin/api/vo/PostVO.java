package com.ztman.admin.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 岗位视图VO
 */

@Data
public class PostVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	/**
	 * 岗位编码
	 */
	private String postCode;
	/**
	 * 岗位名称
	 */
	private String postName;
	/**
	 * 排序
	 */
	private Integer postOder;
	/**
	 * 所属部门
	 */
	private Integer deptId;

	/**
	 * 部门名称
	 */
	private String deptName;

	/**
	 * 是否删除 0：正常 1删除
	 */
	private String delFlag;
	/**
	 * 创建时间
	 */
	private LocalDateTime createtime;
	/**
	 * 修改时间
	 */
	private LocalDateTime updatetime;

}
