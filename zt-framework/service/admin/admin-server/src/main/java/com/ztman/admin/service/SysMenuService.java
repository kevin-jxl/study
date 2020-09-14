package com.ztman.admin.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ztman.admin.api.entity.SysMenu;
import com.ztman.admin.api.vo.MenuVO;
import com.ztman.common.core.util.Query;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author ztman
 * @since 2017-10-29
 */
public interface SysMenuService extends IService<SysMenu> {
	/**
	 * 通过角色编号查询URL 权限
	 *
	 * @param role 角色编号
	 * @return 菜单列表
	 */
	List<MenuVO> findMenuByRoleCode(String role, String platformName);

	/**
	 * 级联删除菜单
	 *
	 * @param id 菜单ID
	 * @return 成功、失败
	 */
	Boolean deleteMenu(Integer id);

	/**
	 * 更新菜单信息
	 *
	 * @param sysMenu 菜单信息
	 * @return 成功、失败
	 */
	Boolean updateMenuById(SysMenu sysMenu);

	Page selectWithMenuVoPage(Query query);

    List getParentOrgOfMenu(Integer i);

    List getMenuByType(Integer menuType);
}
