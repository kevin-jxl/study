package com.ztman.common.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author joy
 * @date 2019/3/1
 * <p>
 * 个性权限控制标签类型
 */
@Getter
@AllArgsConstructor
public enum PowerLabelTypeEnum {
	EQ(0, "等于"),

	UN_EQ(1, "不等于"),

	CONTAINS(2,"包含"),

	UN_CONTAINS(3,"不包含"),

	GREATER_THAN(4,"大于"),

	GREATER_THAN_OR_EQ(5,"大于等于"),

	LESS_THAN(6,"小于"),

	 LESS_THAN_OR_EQ(7,"小于等于");
	/**
	 * 类型
	 */
	private final int type;
	/**
	 * 描述
	 */
	private final String description;
}
