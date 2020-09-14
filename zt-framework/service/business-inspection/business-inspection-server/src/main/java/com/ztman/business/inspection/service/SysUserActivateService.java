package com.ztman.business.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import com.ztman.business.inspection.client.entity.SysUserActivate;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.common.core.util.Query;
/**
 * 系统用户激活 服务接口层
 *
 * @author zxx
 * @date 2020-08-29 16:05:02
 */
public interface SysUserActivateService extends IService<SysUserActivate> {
    /**
     * 查询系统用户激活信息
     *
     * @param tenantId 系统用户激活ID
     * @return 系统用户激活信息
     */
    public SysUserActivate selectSysUserActivateById(String tenantId);

    /**
     * 查询系统用户激活列表
     *
     * @param sysUserActivate 系统用户激活信息
     * @return 系统用户激活集合
     */
    public List<SysUserActivate> selectSysUserActivateList(SysUserActivate sysUserActivate);


    /**
     * 分页模糊查询系统用户激活列表
     * @return 系统用户激活集合
     */
    public Page selectSysUserActivateListByLike(Query query);



    /**
     * 新增系统用户激活
     *
     * @param sysUserActivate 系统用户激活信息
     * @return 结果
     */
    public int insertSysUserActivate(SysUserActivate sysUserActivate);

    /**
     * 修改系统用户激活
     *
     * @param sysUserActivate 系统用户激活信息
     * @return 结果
     */
    public int updateSysUserActivate(SysUserActivate sysUserActivate);

    /**
     * 删除系统用户激活
     *
     * @param tenantId 系统用户激活ID
     * @return 结果
     */
    public int deleteSysUserActivateById(String tenantId);

    /**
     * 批量删除系统用户激活
     *
     * @param tenantIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysUserActivateByIds(String[] tenantIds);

}

