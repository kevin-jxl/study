package com.ztman.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import cn.hutool.core.bean.BeanUtil;
import com.ztman.common.core.util.Query;
import com.ztman.admin.mapper.SysSystemInterfaceMapper;
import com.ztman.admin.api.entity.SysSystemInterface;
import com.ztman.admin.service.SysSystemInterfaceService;

import java.util.List;


@Service("sysSystemInterfaceService")
public class SysSystemInterfaceServiceImpl extends ServiceImpl<SysSystemInterfaceMapper, SysSystemInterface> implements SysSystemInterfaceService {

    @Autowired
    private SysSystemInterfaceMapper sysSystemInterfaceMapper;

    /**
     * 查询系统集成的接口管理信息
     *
     * @param interfaceId 系统集成的接口管理ID
     * @return 系统集成的接口管理信息
     */
    @Override
    public SysSystemInterface selectSysSystemInterfaceById(Integer interfaceId)
    {
        return sysSystemInterfaceMapper.selectSysSystemInterfaceById(interfaceId);
    }

    /**
     * 查询系统集成的接口管理列表
     *
     * @param sysSystemInterface 系统集成的接口管理信息
     * @return 系统集成的接口管理集合
     */
    @Override
    public List<SysSystemInterface> selectSysSystemInterfaceList(SysSystemInterface sysSystemInterface)
    {
        return sysSystemInterfaceMapper.selectSysSystemInterfaceList(sysSystemInterface);
    }


    /**
     * 分页模糊查询系统集成的接口管理列表
     * @return 系统集成的接口管理集合
     */
    @Override
    public Page selectSysSystemInterfaceListByLike(Query query)
    {
        Object sysId = query.getCondition().get("sysId");
        if (sysId != null && !"".equals(sysId)) {
            SysSystemInterface sysSystemInterface =  BeanUtil.mapToBean(query.getCondition(), SysSystemInterface.class,false);
            query.setRecords(sysSystemInterfaceMapper.selectSysSystemInterfaceListByLike(query,sysSystemInterface));
        }
        return query;
    }

    /**
     * 新增系统集成的接口管理
     *
     * @param sysSystemInterface 系统集成的接口管理信息
     * @return 结果
     */
    @Override
    public int insertSysSystemInterface(SysSystemInterface sysSystemInterface)
    {
        return sysSystemInterfaceMapper.insertSysSystemInterface(sysSystemInterface);
    }

    /**
     * 修改系统集成的接口管理
     *
     * @param sysSystemInterface 系统集成的接口管理信息
     * @return 结果
     */
    @Override
    public int updateSysSystemInterface(SysSystemInterface sysSystemInterface)
    {
        return sysSystemInterfaceMapper.updateSysSystemInterface(sysSystemInterface);
    }


    /**
     * 删除系统集成的接口管理
     *
     * @param interfaceId 系统集成的接口管理ID
     * @return 结果
     */
    public int deleteSysSystemInterfaceById(Integer interfaceId)
    {
        return sysSystemInterfaceMapper.deleteSysSystemInterfaceById( interfaceId);
    };


    /**
     * 批量删除系统集成的接口管理对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysSystemInterfaceByIds(String[] interfaceIds)
    {
        return sysSystemInterfaceMapper.deleteSysSystemInterfaceByIds( interfaceIds);
    }

}
