package com.ztman.business.inspection.mapper;

import com.ztman.business.inspection.client.entity.SysUser;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.common.core.util.Query;

/**
 * 系统用户表  mapper层
 *
 * @author zxx
 * @date 2020-08-29 10:28:55
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 查询系统用户表信息
     *
     * @param userId 系统用户表ID
     * @return 系统用户表信息
     */
    public SysUser selectSysUserById(Integer userId);

    /**
     * 查询系统用户表列表
     *
     * @param sysUser 系统用户表信息
     * @return 系统用户表集合
     */
    public List<SysUser> selectSysUserList(SysUser sysUser);

    /**
     * 模糊查询系统用户表列表
     *
     * @param sysUser 系统用户表信息
     * @return 系统用户表集合
     */
    public List<SysUser> selectSysUserListByLike(SysUser sysUser);


    /**
     * 分页模糊查询系统用户表列表
     *
     * @param sysUser 系统用户表信息
     * @return 系统用户表集合
     */
    public List<SysUser> selectSysUserListByLike(Query query, SysUser sysUser);


    /**
     * 新增系统用户表
     *
     * @param sysUser 系统用户表信息
     * @return 结果
     */
    public int insertSysUser(SysUser sysUser);

    /**
     * 修改系统用户表
     *
     * @param sysUser 系统用户表信息
     * @return 结果
     */
    public int updateSysUser(SysUser sysUser);

    /**
     * 删除系统用户表
     *
     * @param userId 系统用户表ID
     * @return 结果
     */
    public int deleteSysUserById(Integer userId);

    /**
     * 批量删除系统用户表
     *
     * @param userIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysUserByIds(String[] userIds);



}
