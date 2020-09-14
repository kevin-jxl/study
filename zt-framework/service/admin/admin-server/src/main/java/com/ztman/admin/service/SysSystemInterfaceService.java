package com.ztman.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.ztman.admin.api.entity.SysSystemInterface;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.common.core.util.Query;

import java.util.List;

/**
 * 系统集成的接口管理 服务接口层
 *
 * @author ztman code generator
 * @date 2020-09-07 16:06:15
 */
public interface SysSystemInterfaceService extends IService<SysSystemInterface> {
    /**
     * 查询系统集成的接口管理信息
     *
     * @param interfaceId 系统集成的接口管理ID
     * @return 系统集成的接口管理信息
     */
    public SysSystemInterface selectSysSystemInterfaceById(Integer interfaceId);

    /**
     * 查询系统集成的接口管理列表
     *
     * @param sysSystemInterface 系统集成的接口管理信息
     * @return 系统集成的接口管理集合
     */
    public List<SysSystemInterface> selectSysSystemInterfaceList(SysSystemInterface sysSystemInterface);


    /**
     * 分页模糊查询系统集成的接口管理列表
     * @return 系统集成的接口管理集合
     */
    public Page selectSysSystemInterfaceListByLike(Query query);



    /**
     * 新增系统集成的接口管理
     *
     * @param sysSystemInterface 系统集成的接口管理信息
     * @return 结果
     */
    public int insertSysSystemInterface(SysSystemInterface sysSystemInterface);

    /**
     * 修改系统集成的接口管理
     *
     * @param sysSystemInterface 系统集成的接口管理信息
     * @return 结果
     */
    public int updateSysSystemInterface(SysSystemInterface sysSystemInterface);

    /**
     * 删除系统集成的接口管理
     *
     * @param interfaceId 系统集成的接口管理ID
     * @return 结果
     */
    public int deleteSysSystemInterfaceById(Integer interfaceId);

    /**
     * 批量删除系统集成的接口管理
     *
     * @param interfaceIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysSystemInterfaceByIds(String[] interfaceIds);

}

