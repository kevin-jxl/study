package com.ztman.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.entity.SysMenu;
import com.ztman.admin.api.vo.MenuVO;
import com.ztman.common.core.util.Query;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author ztman
 * @since 2017-10-29
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

	/**
	 * 通过角色编号查询菜单
	 *
	 * @param role 角色编号
	 * @return
	 */
	List<MenuVO> findMenuByRoleCode(@Param("role") String role, @Param("platformName") String platformName);

	/**
	 * 通过角色ID查询权限
	 *
	 * @param roleIds Ids
	 * @return
	 */
	List<String> findPermissionsByRoleIds(String roleIds);

	List selectWithMenuVoPage(Query query, @Param("parentId") int parseInt, @Param("dataSearch") Object dataSearch,@Param("roleIds")List roleIds);
	List<SysMenu> selectWithMenuVoPage(SysMenu sysMenu,@Param("roleIds")List roleIds);

	List<Integer> getChildrenOrgOfMenu(int parseInt);

	List<Integer> getParentOrgOfMenu(int parentId);

    List getMenuByType(Integer menuType);
}
