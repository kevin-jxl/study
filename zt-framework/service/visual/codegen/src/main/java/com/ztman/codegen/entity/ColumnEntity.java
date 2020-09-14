package com.ztman.codegen.entity;

import lombok.Data;

/**
 * @date 2018/07/29
 * 列属性：
 */
@Data
public class ColumnEntity {
	/**
	 * 是主键
	 */
	private Boolean isPkey;

	/**
	 * 是否为自增列(提示：自增列不一定是主键哦)
	 */
	private Boolean isAutoincrement;

	/**
	 * 列表
	 */
	private String columnName;
	/**
	 * 数据类型
	 */
	private String dataType;
	/**
	 * 备注
	 */
	private String comments;

	/**
	 * 驼峰属性
	 */
	private String caseAttrName;
	/**
	 * 普通属性
	 */
	private String lowerAttrName;
	/**
	 * 属性类型
	 */
	private String attrType;
	/**
	 * 其他信息。
	 */
	private String extra;
}
