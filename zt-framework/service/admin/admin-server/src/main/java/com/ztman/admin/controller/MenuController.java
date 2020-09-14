package com.ztman.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.dto.MenuTree;
import com.ztman.admin.api.dto.TreeNode;
import com.ztman.admin.api.entity.SysMenu;
import com.ztman.admin.api.entity.SysRole;
import com.ztman.admin.api.entity.SysRoleMenu;
import com.ztman.admin.api.vo.MenuVO;
import com.ztman.admin.service.SysMenuService;
import com.ztman.admin.service.SysRoleMenuService;
import com.ztman.admin.service.SysRoleService;
import com.ztman.admin.api.vo.TreeUtil;
import com.ztman.common.core.util.Query;
import com.ztman.common.log.annotation.SysLog;
import com.ztman.common.security.DTO.SecruityUser;
import com.ztman.common.security.util.SecurityUtils;
import com.ztman.common.core.constant.CommonConstant;
import com.ztman.common.core.util.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author ztman
 * @date 2017/10/31
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysRoleService sysRoleService;
    /**
     * 通过角色名称查询用户菜单
     *
     * @param role 角色名称
     * @return 菜单列表
     */
    @GetMapping("/findMenuByRole/{role}")
    public List<MenuVO> findMenuByRole(@PathVariable String role) {

        return sysMenuService.findMenuByRoleCode(role,"");
    }

    /**
     * 返回当前用户的左侧树形菜单集合
     *
     * @return 当前用户的树形菜单
     */
    @GetMapping(value = "/userMenu")
    public List<MenuTree> userMenu(@RequestParam(required = false) String platformName) {
        int parentId = 1;
        if(StringUtils.isEmpty(platformName)){
            platformName = CommonConstant.ZTFRAMEWOEK;
        }else if(platformName.equals(CommonConstant.ZTSPARK)){
            platformName = CommonConstant.ZTSPARK;
            parentId = 100;
        }else{
            platformName = "";
        }
        // 获取符合条件得菜单
        SecruityUser userInfo = SecurityUtils.getUserInfo();
        Set<MenuVO> all = new HashSet<>();
        List<String> roles = SecurityUtils.getRoles();
        if (roles != null && roles.size() > 0) {
            for (String roleName : roles) {
                List<MenuVO> menu = sysMenuService.findMenuByRoleCode(roleName,platformName);
                all.addAll(menu);
            }
        }
        List<MenuTree> menuTreeList = new ArrayList<>();
        all.forEach(menuVo -> {
            if (!CommonConstant.BUTTON.equals(menuVo.getType()) && !CommonConstant.LIST.equals(menuVo.getType())) {
                    menuTreeList.add(new MenuTree(menuVo));
            }
        });
        CollUtil.sort(menuTreeList, Comparator.comparingInt(MenuTree::getSort));
        return TreeUtil.bulid(menuTreeList, parentId);
    }

    /**
     * 返回当前用户的菜单管理树形菜单集合
     *
     * @return 当前用户的树形菜单
     */
    @GetMapping(value = "/userMenuTree")
    public List<MenuTree> allUserMenuTree() {
        // 获取符合条件得菜单
        Set<MenuVO> all = new HashSet<>();
        List<String> roles = SecurityUtils.getRoles();
        if(roles.contains(CommonConstant.ROLE_ADMIN)){
            return getTree();
        }
        if (roles != null && roles.size() > 0) {
            for (String roleName : roles) {
                List<MenuVO> menu = sysMenuService.findMenuByRoleCode(roleName,"");
                all.addAll(menu);
            }
        }
        List<MenuTree> menuTreeList = new ArrayList<>();
        all.forEach(menuVo -> {

            menuTreeList.add(new MenuTree(menuVo));
        });
        CollUtil.sort(menuTreeList, Comparator.comparingInt(MenuTree::getSort));
        return TreeUtil.bulid(menuTreeList, -1);
    }
    /**
     * 返回树形菜单集合
     *
     * @return 树形菜单
     */
    @GetMapping(value = "/allTree")
    public List<MenuTree> getTree() {
        SysMenu condition = new SysMenu();
        condition.setDelFlag("1");
        List<SysMenu> sysMenus = sysMenuService.selectList(new EntityWrapper<>(condition));
        return TreeUtil.bulidTree(sysMenus, -1);
    }

    /**
     * 返回角色的菜单集合
     *
     * @param roleName 角色名称
     * @return 属性集合
     */
    @GetMapping("/roleTree/{roleName}")
    public List<MenuTree> roleTree(@PathVariable String roleName) {
        List<String> roles = SecurityUtils.getRoles();
        SysMenu condition = new SysMenu();
        condition.setDelFlag(CommonConstant.STATUS_NORMAL);
        if(CommonConstant.ROLE_ADMIN.equals(roleName) && roles.contains(CommonConstant.ROLE_ADMIN)){
            return AdminMenu();
        }
        List<MenuTree> trees = allUserMenuTree();
        List<MenuVO> menus = sysMenuService.findMenuByRoleCode(roleName,"");
        List<Integer> menuList = new ArrayList<>();
        menus.forEach(menuVo -> {
                menuList.add(menuVo.getMenuId());
        });
        for (MenuVO menuVo : menus) {
            menuList.add(menuVo.getMenuId());
        }
        if (menuList != null && menuList.size() > 0 && trees != null && trees.size() > 0) {
            trees = menuSelected(trees, menuList);
        }
        CollUtil.sort(trees, Comparator.comparingInt(MenuTree::getSort));
        //增加该角色附属权限菜单id
        tressForeach(trees, roleName);
        return trees;
    }
    public void tressForeach( List<MenuTree> trees,String roleName){
        String roleCode=roleName;
        for (MenuTree menuTree: trees) {
            List<Integer> checkIdList = sysRoleService.selectListRoleId(null,roleCode,menuTree.getName());
            menuTree.setCheckIdList(checkIdList);
        }

    }

    public  List<MenuTree> AdminMenu(){
        SysMenu condition = new SysMenu();
        condition.setDelFlag(CommonConstant.STATUS_NORMAL);
        List<MenuTree> trees = TreeUtil.bulidTree(sysMenuService.selectList(new EntityWrapper<>(condition)), -1);
        List<MenuVO> menus = sysMenuService.findMenuByRoleCode(CommonConstant.ROLE_ADMIN,"");
        List<Integer> menuList = new ArrayList<>();
        menus.forEach(menuVo -> {
                menuList.add(menuVo.getMenuId());
        });
        if (menuList != null && menuList.size() > 0 && trees != null && trees.size() > 0) {
            trees = menuSelected(trees, menuList);
        }
        //增加该角色附属权限菜单id
        tressForeach(trees, CommonConstant.ROLE_ADMIN);
        return trees;
    }
    public <T extends TreeNode> List<T> menuSelected(List<T> trees, List<Integer> menuList) {
        for (TreeNode menuTree : trees) {
            for (Integer integer : menuList) {
                if (menuTree.getId() == integer) {
                    menuTree.setChecked(true);
                }
            }
            List<TreeNode> children = menuTree.getChildren();
            if (children != null && children.size() > 0) {
                for (TreeNode treeNode : children) {
                    menuSelected(children, menuList);
                }
            }
        }
        return trees;
    }

    /**
     * 通过ID查询菜单的详细信息
     *
     * @param id 菜单ID
     * @return 菜单详细信息
     */
    @GetMapping("/{id}")
    public MenuVO menu(@PathVariable Integer id) {
        MenuVO menuVO = new MenuVO();
        SysMenu sysMenu = sysMenuService.selectById(id);
        if (sysMenu != null) {
            List list = sysMenuService.getParentOrgOfMenu(id);
            BeanUtils.copyProperties(sysMenu, menuVO);
            menuVO.setMenuList(list);
        }
        return menuVO;
    }

    /**
     * 新增菜单
     *
     * @param sysMenu 菜单信息
     * @return success/false
     */
    @SysLog("菜单新增")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_menu_add')")
    public R<Boolean> menu(@RequestBody SysMenu sysMenu) {
        if(sysMenu.getParentId()==null){
            sysMenu.setParentId(-1);
        }
        sysMenu.setIcon(sysMenu.getIcons());
        sysMenu.setPlatformName("基础平台");
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        Boolean b = sysMenuService.insert(sysMenu);
        if(SecurityUtils.getRoles().contains(CommonConstant.ROLE_ADMIN)){
            sysRoleMenu.setRoleId(1);
            sysRoleMenu.setMenuId(sysMenu.getMenuId());
            sysRoleMenuService.insert(sysRoleMenu);
        }else{
            SysRole sysRole = new SysRole();
            sysRole.setRoleCode(SecurityUtils.getRoles().get(0));
            SysRole roleDetails = sysRoleService.selectOne(new EntityWrapper<>(sysRole));
            sysRoleMenu.setRoleId(roleDetails.getRoleId());
            sysRoleMenu.setMenuId(sysMenu.getMenuId());
            sysRoleMenuService.insert(sysRoleMenu);
        }
        return new R<>(b);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return success/false
     * TODO  级联删除下级节点
     */
    @SysLog("菜单删除")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('sys_menu_del')")
    public R<Boolean> menuDel(@PathVariable Integer id) {
        return new R<>(sysMenuService.deleteMenu(id));
    }

    @SysLog("菜单修改")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('sys_menu_edit')")
    public R<Boolean> menuUpdate(@RequestBody SysMenu sysMenu) {
        sysMenu.setIcon(sysMenu.getIcons());
        return new R<>(sysMenuService.updateMenuById(sysMenu));
    }

    /**
     * 　　* @Description: 分页查询菜单
     * 　　* @param
     * 　　* @return
     * 　　* @author lmh
     * 　　* @date 2019/1/30 17:23
     */
    @GetMapping("/menuPage")
    public Page menuPage(@RequestParam Map<String, Object> params) {
        return sysMenuService.selectWithMenuVoPage(new Query(params));
    }

    /**
     * 　　* @Description: 根据编码查询父ID集合
     * 　　* @param
     * 　　* @return
     * 　　* @author lmh
     * 　　* @date 2019/2/12 17:12
     */
    @GetMapping("/getParentOrgOfMenu")
    public List getParentOrgOfMenu(@RequestParam Map<String, Object> params) {
        int parentId = Integer.parseInt((String) params.get("parentId"));
        return sysMenuService.getParentOrgOfMenu(parentId);
    }

    /**
     * 根据类型查询菜单
     *
     * @param menuType
     * @return
     */
    @GetMapping("/getMenuByType")
    public List getMenuByType(@RequestParam(required = true) Integer menuType) {
        return sysMenuService.getMenuByType(menuType);
    }
}
