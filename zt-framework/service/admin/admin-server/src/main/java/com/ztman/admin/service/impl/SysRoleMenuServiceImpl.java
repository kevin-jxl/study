package com.ztman.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.admin.api.entity.SysRoleMenu;
import com.ztman.admin.mapper.SysRoleMenuMapper;
import com.ztman.admin.service.SysRoleMenuService;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 角色菜单表 服务实现类
 * </p>
 *
 * @author ztman
 * @since 2017-10-29
 */
@Service
@AllArgsConstructor
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
	private CacheManager cacheManager;

	/**
	 * @param role
	 * @param roleId  角色
	 * @param menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
	 * @return
	 */
	@Override
	@CacheEvict(value = "menu_details", key = "#role + '_menu'")
	public Boolean insertRoleMenus(String role, Integer roleId, String menuIds,String menuName) {
		SysRoleMenu condition = new SysRoleMenu();
		condition.setRoleId(roleId);
		condition.setMenuType(menuName);
		this.delete(new EntityWrapper<>(condition));
		List<SysRoleMenu> roleMenuList = new ArrayList<>();
		List<String> menuIdList = Arrays.asList(menuIds.split(","));
		if (CollUtil.isEmpty(menuIdList)) {
			return Boolean.TRUE;
		}
		for (String menuId : menuIdList) {
			SysRoleMenu roleMenu = new SysRoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(Integer.valueOf(menuId));
			roleMenu.setMenuType(menuName);
			roleMenuList.add(roleMenu);
		}
		//清空userinfo
		cacheManager.getCache("user_details").clear();
		return this.insertBatch(roleMenuList);
	}
}
