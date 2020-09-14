package com.ztman.admin.api.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 菜单权限表
 * </p>
 */
@Data
public class MenuVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private Integer menuId;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单权限标识
     */
    private String permission;
    /**
     * 父菜单ID
     */
    private Integer parentId;

    /**
     * 父菜单名字
     */
    private String parentName;
    /**
     * 图标
     */
    private String icons;
    private String icon;
    /**
     * 一个路径
     */
    private String path;
    /**
     * VUE页面
     */
    private String component;
    /**
     * 排序值
     */
    private Integer sort;
    /**
     * 菜单类型 （0菜单 1按钮）
     */
    private String type;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 0--正常 1--删除
     */
    private String delFlag;

    /**
     * 菜单集合
     */
    private List menuList;


    @Override
    public int hashCode() {
        return menuId.hashCode();
    }

    /**
     * menuId 相同则相同
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MenuVO) {
            Integer targetMenuId = ((MenuVO) obj).getMenuId();
            return menuId.equals(targetMenuId);
        }
        return super.equals(obj);
    }
}
