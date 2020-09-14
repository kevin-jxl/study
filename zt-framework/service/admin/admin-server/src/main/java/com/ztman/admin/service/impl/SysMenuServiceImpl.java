package com.ztman.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.admin.api.entity.SysMenu;
import com.ztman.admin.api.vo.MenuVO;
import com.ztman.admin.api.vo.RoleVO;
import com.ztman.admin.mapper.SysMenuMapper;
import com.ztman.admin.mapper.SysUserMapper;
import com.ztman.admin.service.SysMenuService;
import com.ztman.common.core.constant.CommonConstant;
import com.ztman.common.core.util.Query;
import com.ztman.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author ztman
 * @since 2017-10-29
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    //@Cacheable(value = "menu_details", key = "#role  + '_menu'")
    public List<MenuVO> findMenuByRoleCode(String role, String platformName) {
        return sysMenuMapper.findMenuByRoleCode(role,platformName);
    }

    @Override
    @CacheEvict(value = "menu_details", allEntries = true)
    public Boolean deleteMenu(Integer id) {
        // 删除当前节点
        SysMenu condition1 = new SysMenu();
        condition1.setMenuId(id);
        condition1.setDelFlag(CommonConstant.STATUS_DEL);
        this.updateById(condition1);

        // 删除父节点为当前节点的节点
        SysMenu conditon2 = new SysMenu();
        conditon2.setParentId(id);
        SysMenu sysMenu = new SysMenu();
        sysMenu.setDelFlag(CommonConstant.STATUS_DEL);
        return this.update(sysMenu, new EntityWrapper<>(conditon2));
    }

    @Override
    @CacheEvict(value = "menu_details", allEntries = true)
    public Boolean updateMenuById(SysMenu sysMenu) {
        return this.updateById(sysMenu);
    }

    @Override
    public Page selectWithMenuVoPage(Query query) {
        //获取点击的父编码
        Object parentId = query.getCondition().get("parentId");
        //查询内容
        Object menuSearch = query.getCondition().get("menuSearch");
        ;
        List<RoleVO> roles = sysUserMapper.selectUserRoleByUserId(SecurityUtils.getUserInfo().getId());
        List<Integer> roleIds = new ArrayList<>();
        for (RoleVO ro:roles) {
            roleIds.add(ro.getRoleId());
        }
        List list = sysMenuMapper.selectWithMenuVoPage(query, Integer.parseInt((String) parentId), menuSearch,roleIds);
        query.setRecords(list);
        return query;
    }

    @Override
    public List getParentOrgOfMenu(Integer i) {
        List list = sysMenuMapper.getParentOrgOfMenu(i);
        list.remove(list.size()-1);
        list.add(0, -1);
        return list;
    }

    @Override
    public List getMenuByType(Integer menuType) {
        return sysMenuMapper.getMenuByType(menuType);
    }
}
