package com.ztman.admin.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.dto.DeptTree;
import com.ztman.admin.api.dto.RoleDTO;
import com.ztman.admin.api.dto.TreeNode;
import com.ztman.admin.api.entity.SysDept;
import com.ztman.admin.api.entity.SysRole;
import com.ztman.admin.api.entity.SysRoleMenu;
import com.ztman.admin.api.entity.SysUser;
import com.ztman.admin.api.vo.RoleVO;
import com.ztman.admin.service.SysDeptService;
import com.ztman.admin.service.SysRoleMenuService;
import com.ztman.admin.service.SysRoleService;

import com.ztman.common.core.constant.CommonConstant;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import com.ztman.common.log.annotation.SparkLog;
import com.ztman.common.log.annotation.SysLog;
import com.ztman.common.log.enums.LogType;
import com.ztman.common.log.util.EsLog;
import com.ztman.common.security.DTO.SecruityUser;
import com.ztman.common.security.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 通过ID查询角色信息
     *
     * @param id ID
     * @return 角色信息
     */
    @GetMapping("/{id}")
    public SysRole role(@PathVariable Integer id) {
        return sysRoleService.selectById(id);
    }

    /**
     * 添加角色
     *
     * @param roleDto 角色信息
     * @return success、false
     */
    @SysLog("添加角色")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_role_add')")
    @SparkLog(title = "角色管理",desc = "添加角色")
    public R<Boolean> role(@RequestBody RoleDTO roleDto) {
        if (roleDto.getCreateBy() == null) {
            Integer id = SecurityUtils.getUserInfo().getId();
            roleDto.setCreateBy(id);
        }
        String rolecode = getRandomString(16);
        roleDto.setRoleCode(getRoleCode(rolecode));
        roleDto.setRoleFlag(CommonConstant.STATUS_NORMAL);
        return new R<>(sysRoleService.insertRole(roleDto));
    }
    private String getRoleCode(String rolecode){
        Object o = redisTemplate.opsForValue().get(rolecode);
        if (o != null && !"".equals(o)) {
            rolecode = getRandomString(16);
            getRoleCode(rolecode);
        }
        redisTemplate.opsForValue().set(rolecode,rolecode,100);
        return rolecode;
    }

    /**
     * 修改角色
     *
     * @param roleDto 角色信息
     * @return success/false
     */
    @SysLog("修改角色")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('sys_role_edit')")
    public R<Boolean> roleUpdate(@RequestBody RoleDTO roleDto) {
        //插入log日志
        SecruityUser user = SecurityUtils.getUserInfo();
        log.info("es_begin:" + JSONUtil.toJsonStr(new EsLog(String.valueOf(user.getId()), user.getUserName(), user.getEmpNo(), user.getDeptId(), "", "/role/update", "PUT", JSONUtil.toJsonStr(roleDto), "", "", DateUtil.now(), "", LogType.spark_OPERATE.toString(), "", "")) + "es_end!");
        return new R<>(sysRoleService.updateRoleById(roleDto));
    }
    @SysLog("删除角色")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('sys_role_del')")
    public R<Boolean> roleDel(@PathVariable Integer id) {
        SysRole sysRole = sysRoleService.selectById(id);
        sysRole.setDelFlag(CommonConstant.STATUS_DEL);
        if(id==1){
            return new R<>(false,"此角色不允许删除");
        }
        return new R<>(sysRoleService.updateById(sysRole));
    }

    /**
     * 分页查询角色信息
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @GetMapping("/rolePage")
    public Page rolePage(@RequestParam Map<String, Object> params) {
        List<String> roles = SecurityUtils.getRoles();
        if(roles.contains(CommonConstant.ROLE_ADMIN)){
            params.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);

            return sysRoleService.selectRolePageAllByRoleName(new Query<>(params), new EntityWrapper<>());
        }
        params.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
        params.put(CommonConstant.CREATE_BY, SecurityUtils.getUserInfo().getId());
        params.put("companyId", SecurityUtils.getUserInfo().getCompanyId());
        params.put("tenantId", SecurityUtils.getUserInfo().getTenantId());
        return sysRoleService.selectRolePageByRoleName(new Query<>(params), new EntityWrapper<>());
    }

    /**
     * 更新角色菜单
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
     * @return success、false
     */
    @SysLog("更新角色菜单")
    @PutMapping("/roleMenuUpd")
    @PreAuthorize("@pms.hasPermission('sys_role_perm')")
    public R<Boolean> roleMenuUpd(Integer roleId, @RequestParam(value = "menuIds", required = false) String menuIds,@RequestParam(value = "menuName", required = false) String menuName) {
        SysRole sysRole = sysRoleService.selectById(roleId);
        return new R<>(sysRoleMenuService.insertRoleMenus(sysRole.getRoleCode(), roleId, menuIds,menuName));
    }
    @SysLog("更新角色菜单")
    @PutMapping("/insertAdminRole")
    public R<Boolean> insertAdminRole() {
        for (int i = 1; i < 100; i++) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(1);
            sysRoleMenu.setMenuId(i);
            sysRoleMenu.setMenuType("基础平台");
            sysRoleMenuService.insert(sysRoleMenu);
        }

        return new R<>();
    }
    /**
     * 　　* @Description: 查询所有角色
     * 　　* @param
     * 　　* @return
     * 　　* @author lmh
     * 　　* @date 2019/1/28 11:17
     */
    @GetMapping("/roleListAll")
    public List<SysRole> roleListAll() {
        SysRole condition = new SysRole();
        condition.setCreateBy(SecurityUtils.getUserInfo().getId());
        return sysRoleService.selectList(new EntityWrapper<>(condition));
    }
    /**
     *@Description: 查询所有角色
     * @param
     * @return
     * @author wsd
     */
    @GetMapping("/roleUserList")
    public List<SysRole> roleUserList() {
        Map<String, Object> params = new HashMap<>();
        SysRole condition = new SysRole();
        List<String> roles = SecurityUtils.getRoles();
        params.put("page", 1);
        params.put("limit", 99999999);

        if(roles.contains(CommonConstant.ROLE_ADMIN)){
            params.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
            params.put("role_flag", CommonConstant.STATUS_NORMAL);
            return sysRoleService.selectRolePageAllByRoleName(new Query<>(params), new EntityWrapper<>()).getRecords();
        }
        params.put("companyId", SecurityUtils.getUserInfo().getCompanyId());
        params.put("tenantId", SecurityUtils.getUserInfo().getTenantId());
        params.put(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
        params.put("role_flag", CommonConstant.STATUS_NORMAL);
        params.put(CommonConstant.CREATE_BY, SecurityUtils.getUserInfo().getId());
        params.put("companyId", SecurityUtils.getUserInfo().getCompanyId());
        return sysRoleService.selectRolePageByRoleName(new Query<>(params), new EntityWrapper<>()).getRecords();

//        return sysRoleService.selectList(new EntityWrapper<>(condition).orderBy("role_name",false));
    }


    /**
     * 查询角色数据权限
     *
     * @param roleId
     * @return
     */
    @GetMapping("/selectRoledataPower")
    public RoleVO selectRoledataPower(@RequestParam(required = true) Integer roleId) {

        RoleVO roleVO = new RoleVO();//返回的ov包装类
        SysRole sysRole = sysRoleService.selectById(roleId);
        if (sysRole != null) {
            BeanUtils.copyProperties(sysRole, roleVO);
            Integer dsType = sysRole.getDsType();//数据权限类型0-全部 1-自定义 2：本级及子级 3：本级
            String dsScope = sysRole.getDsScope();//数据类型为自定义的数据集合
            //查询部门树
            SysDept condition = new SysDept();
            condition.setDelFlag(CommonConstant.STATUS_NORMAL);
            List<DeptTree> deptTrees = sysDeptService.selectListTree(new EntityWrapper<>(condition));
            //处理部门树的默认选中值
            if (deptTrees != null && deptTrees.size() > 0 && dsScope != null && !"".equals(dsScope)) {
                List<Integer> deptList = new ArrayList<>();
                //将字符串分割为数组
                String[] split = dsScope.split(",");
                if (split != null && split.length > 0) {
                    for (String s : split) {
                        deptList.add(Integer.valueOf(s));
                    }
                    //两次for循环处理数结构选中值
                    deptTrees = deptSelected(deptTrees, deptList);
                }
            }
            roleVO.setDeptTrees(deptTrees);
        }
        return roleVO;
    }

    //树结构选中值处理
    public <T extends TreeNode> List<T> deptSelected(List<T> trees, List<Integer> menuList) {
        for (TreeNode menuTree : trees) {
            for (Integer integer : menuList) {
                if (menuTree.getId() == integer) {
                    menuTree.setChecked(true);
                }
            }
            List<TreeNode> children = menuTree.getChildren();
            if (children != null && children.size() > 0) {
                for (TreeNode treeNode : children) {
                    deptSelected(children, menuList);
                }
            }
        }
        return trees;
    }


    /**
     * 更新角色数据权限
     *
     * @param roleId
     * @param dsType
     * @param dsScope
     * @return
     */
    @GetMapping("/updateRoledataPower")
    public R<Boolean> updateRoledataPower(@RequestParam(required = true) Integer roleId,
                                          @RequestParam(required = true) Integer dsType,
                                          @RequestParam(required = false) String dsScope) {
        SysRole sysRole = new SysRole();
        sysRole.setRoleId(roleId);
        sysRole.setDsType(dsType);
        if (dsType != 1) {
            dsScope = "";

        }
        sysRole.setDsScope(dsScope);
        boolean b = sysRoleService.updateById(sysRole);
        return new R<>(b);
    }

    /**
     * 根据角色查询用户
     *
     * @param roles
     * @return
     */
    @PostMapping("/selectUserByRole")
    public List<SysUser> selectUserByRole(@RequestParam List<Integer> roles) {
        List list = new ArrayList();
        if (roles != null && roles.size() > 0) {
            list = sysRoleService.selectUserByRole(roles);
        }
        return list;
    }

    /**
     * 根据角色查询用户
     *
     * @param roleId
     * @return
     */
    @GetMapping("/selectListRoleId")
    public R selectListRoleId(@RequestParam(required = true) Integer roleId) {
        List<Integer> roleIdList = sysRoleService.selectListRoleId(roleId,null,null);
        return new R(roleIdList);
    }

    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString()+new SimpleDateFormat("YYYYMMDDHHmmss").format(new Date());
    }

}
