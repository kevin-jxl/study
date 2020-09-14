package com.ztman.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.admin.api.dto.RoleDTO;
import com.ztman.admin.api.entity.SysRole;
import com.ztman.admin.mapper.SysRoleDeptMapper;
import com.ztman.admin.mapper.SysRoleDeptPostMapper;
import com.ztman.admin.mapper.SysRoleMapper;
import com.ztman.admin.mapper.SysUserMapper;
import com.ztman.admin.service.SysRoleService;
import com.ztman.common.core.util.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ztman
 * @since 2017-10-29
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysRoleDeptMapper sysRoleDeptMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleDeptPostMapper sysRoleDeptPostMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 添加角色
     *
     * @param roleDto 角色信息
     * @return 成功、失败
     */
    @Override
    public Boolean insertRole(RoleDTO roleDto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleDto, sysRole);
        sysRoleMapper.insert(sysRole);
        return true;
    }

    /**
     * 分页查角色列表
     *
     * @param query   查询条件
     * @param wrapper wapper
     * @return page
     */
    @Override
    public Page selectwithDeptPage(Query<Object> query, EntityWrapper<Object> wrapper) {
        query.setRecords(sysRoleMapper.selectRolePage(query, query.getCondition()));
        return query;
    }


    /**
     * 更新角色--新
     *
     * @param roleDto 含有部门信息
     * @return 成功、失败
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateRoleById(RoleDTO roleDto) {
        //更新角色信息
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleDto, sysRole);
        sysRoleMapper.updateById(sysRole);
        return true;
    }

    /**
     * 通过用户ID，查询角色信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysRole> findRolesByUserId(Integer userId) {
        return sysRoleMapper.findRolesByUserId(userId);
    }

    @Override
    public Page selectRolePageByRoleName(Query<Object> query, EntityWrapper<Object> wrapper) {
        Map<String, Object> cons = query.getCondition();
        String roleName = (String) query.getCondition().get("roleName");
        SysRole sysRole = new SysRole();
        sysRole.setRoleName(roleName);
        query.setRecords(sysRoleMapper.selectRolePageByRoleName(query, query.getCondition()));
        return query;
    }

    /**
     * 根据角色查询用户
     *
     * @param roles
     * @return
     */
    @Override
    public List selectUserByRole(List<Integer> roles) {
        return sysUserMapper.selectUserByRole(roles);
    }

    @Override
    public List<Integer> selectListRoleId(Integer roleId,String roleCode,String ptName) {
        return sysRoleMapper.selectListRoleId(roleId,roleCode,ptName);
    }

    @Override
    public Page selectRolePageAllByRoleName(Query<Object> query, EntityWrapper<Object> objectEntityWrapper) {
        Map<String, Object> cons = query.getCondition();
        String roleName = (String) query.getCondition().get("roleName");
        SysRole sysRole = new SysRole();
        sysRole.setRoleName(roleName);
        query.setRecords(sysRoleMapper.selectRolePageAllByRoleName(query, query.getCondition()));
        return query;
    }

    @Override
    public void deleteDefaultCreateRole(SysRole sss) {
        sysRoleMapper.deleteDefaultCreateRole(sss);
    }
}
