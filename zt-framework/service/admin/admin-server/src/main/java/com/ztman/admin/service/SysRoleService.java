package com.ztman.admin.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ztman.admin.api.dto.RoleDTO;
import com.ztman.admin.api.entity.SysRole;
import com.ztman.common.core.util.Query;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ztman
 * @since 2017-10-29
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 添加角色
     *
     * @param roleDto 角色信息
     * @return 成功、失败
     */
    Boolean insertRole(RoleDTO roleDto);

    /**
     * 分页查角色列表
     *
     * @param objectQuery         查询条件
     * @param objectEntityWrapper wapper
     * @return page
     */
    Page selectwithDeptPage(Query<Object> objectQuery, EntityWrapper<Object> objectEntityWrapper);

    /**
     * 更新角色
     *
     * @param roleDto 含有部门信息
     * @return 成功、失败
     */
    Boolean updateRoleById(RoleDTO roleDto);


    /**
     * 通过用户ID，查询角色信息
     *
     * @param userId
     * @return
     */
    List<SysRole> findRolesByUserId(Integer userId);

    /**
     * 通过角色名称查询角色列表
     *
     * @param objectQuery 查询条件
     * @return
     */
    Page selectRolePageByRoleName(Query<Object> objectQuery, EntityWrapper<Object> objectEntityWrapper);

    /**
     * 根据角色查询用户
     *
     * @param roles
     * @return
     */
    List selectUserByRole(List<Integer> roles);

    List<Integer> selectListRoleId(Integer roleId,String roleCode,String ptName);

    Page selectRolePageAllByRoleName(Query<Object> objectQuery, EntityWrapper<Object> objectEntityWrapper);

    void deleteDefaultCreateRole(SysRole sss);
}
