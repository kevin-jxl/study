package com.ztman.admin.api.dto;

import com.ztman.admin.api.vo.MenuVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单树
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserMenuTree extends UserTreeNode {
	private String icons;
	private String icon;
	private String name;
	private boolean spread = false;
	private String path;
	private String component;
	private String authority;
	private String redirect;
	private String code;
	private String type;
	private String label;
	private Integer sort;

	public UserMenuTree() {
	}

	public UserMenuTree(int id, String name, int parentId) {
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.label = name;
	}

	public UserMenuTree(int id, String name, UserMenuTree parent) {
		this.id = id;
		this.parentId = parent.getId();
		this.name = name;
		this.label = name;
		this.icon = parent.getIcon();
	}

	public UserMenuTree(MenuVO menuVo) {
		this.id = menuVo.getMenuId();
		this.parentId = menuVo.getParentId();
		this.icons = menuVo.getIcons();
		this.icon = menuVo.getIcon();
		this.name = menuVo.getName();
		this.path = menuVo.getPath();
		this.component = menuVo.getComponent();
		this.type = menuVo.getType();
		this.label = menuVo.getName();
		this.sort = menuVo.getSort();
	}
}
