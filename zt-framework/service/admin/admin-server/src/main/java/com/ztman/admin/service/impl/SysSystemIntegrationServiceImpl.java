package com.ztman.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.admin.api.entity.SysSystemIntegration;
import com.ztman.admin.api.vo.SysSystemIntegrationVO;
import com.ztman.admin.mapper.SysSystemIntegrationMapper;
import com.ztman.admin.service.SysSystemIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import cn.hutool.core.bean.BeanUtil;
import com.ztman.common.core.util.Query;

import java.util.ArrayList;
import java.util.List;


@Service("sysSystemIntegrationService")
public class SysSystemIntegrationServiceImpl extends ServiceImpl<SysSystemIntegrationMapper, SysSystemIntegration> implements SysSystemIntegrationService {

    @Autowired
    private SysSystemIntegrationMapper sysSystemIntegrationMapper;

    /**
     * 查询1、系统集成、2、物联网集成信息
     *
     * @param systemId 1、系统集成、2、物联网集成ID
     * @return 1、系统集成、2、物联网集成信息
     */
    @Override
    public SysSystemIntegration selectSysSystemIntegrationById(Integer systemId)
    {
        return sysSystemIntegrationMapper.selectSysSystemIntegrationById(systemId);
    }

    /**
     * 查询1、系统集成、2、物联网集成列表
     *
     * @param sysSystemIntegration 1、系统集成、2、物联网集成信息
     * @return 1、系统集成、2、物联网集成集合
     */
    @Override
    public List<SysSystemIntegration> selectSysSystemIntegrationList(SysSystemIntegration sysSystemIntegration)
    {
        return sysSystemIntegrationMapper.selectSysSystemIntegrationList(sysSystemIntegration);
    }


    /**
     * 分页模糊查询1、系统集成、2、物联网集成列表
     * @return 1、系统集成、2、物联网集成集合
     */
    @Override
    public Page selectSysSystemIntegrationListByLike(Query query)
    {
        SysSystemIntegration sysSystemIntegration =  BeanUtil.mapToBean(query.getCondition(), SysSystemIntegration.class,false);
        query.setRecords(sysSystemIntegrationMapper.selectSysSystemIntegrationListByLike(query,sysSystemIntegration));
        return query;
    }

    /**
     * 新增1、系统集成、2、物联网集成
     *
     * @param sysSystemIntegration 1、系统集成、2、物联网集成信息
     * @return 结果
     */
    @Override
    public int insertSysSystemIntegration(SysSystemIntegration sysSystemIntegration)
    {
        return sysSystemIntegrationMapper.insertSysSystemIntegration(sysSystemIntegration);
    }

    /**
     * 修改1、系统集成、2、物联网集成
     *
     * @param sysSystemIntegration 1、系统集成、2、物联网集成信息
     * @return 结果
     */
    @Override
    public int updateSysSystemIntegration(SysSystemIntegration sysSystemIntegration)
    {
        return sysSystemIntegrationMapper.updateSysSystemIntegration(sysSystemIntegration);
    }


    /**
     * 删除1、系统集成、2、物联网集成
     *
     * @param systemId 1、系统集成、2、物联网集成ID
     * @return 结果
     */
    public int deleteSysSystemIntegrationById(Integer systemId)
    {
        return sysSystemIntegrationMapper.deleteSysSystemIntegrationById( systemId);
    };


    /**
     * 批量删除1、系统集成、2、物联网集成对象
     *
     * @param systemIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysSystemIntegrationByIds(String[] systemIds)
    {
        return sysSystemIntegrationMapper.deleteSysSystemIntegrationByIds( systemIds);
    }

    @Override
    public List<SysSystemIntegrationVO> selectSysSystemIntegrationListJob(SysSystemIntegration integration) {
        return sysSystemIntegrationMapper.selectSysSystemIntegrationListJob();
    }
}
