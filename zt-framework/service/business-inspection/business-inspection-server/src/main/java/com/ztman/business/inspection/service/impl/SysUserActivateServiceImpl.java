package com.ztman.business.inspection.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import com.ztman.common.core.util.Query;
import com.ztman.business.inspection.mapper.SysUserActivateMapper;
import com.ztman.business.inspection.client.entity.SysUserActivate;
import com.ztman.business.inspection.service.SysUserActivateService;


@Service("sysUserActivateService")
public class SysUserActivateServiceImpl extends ServiceImpl<SysUserActivateMapper, SysUserActivate> implements SysUserActivateService {

    @Autowired
    private SysUserActivateMapper sysUserActivateMapper;

    /**
     * 查询系统用户激活信息
     *
     * @param tenantId 系统用户激活ID
     * @return 系统用户激活信息
     */
    @Override
    public SysUserActivate selectSysUserActivateById(String tenantId)
    {
        return sysUserActivateMapper.selectSysUserActivateById(tenantId);
    }

    /**
     * 查询系统用户激活列表
     *
     * @param sysUserActivate 系统用户激活信息
     * @return 系统用户激活集合
     */
    @Override
    public List<SysUserActivate> selectSysUserActivateList(SysUserActivate sysUserActivate)
    {
        return sysUserActivateMapper.selectSysUserActivateList(sysUserActivate);
    }


    /**
     * 分页模糊查询系统用户激活列表
     * @return 系统用户激活集合
     */
    @Override
    public Page selectSysUserActivateListByLike(Query query)
    {
        SysUserActivate sysUserActivate =  BeanUtil.mapToBean(query.getCondition(), SysUserActivate.class,false);
        query.setRecords(sysUserActivateMapper.selectSysUserActivateListByLike(query,sysUserActivate));
        return query;
    }

    /**
     * 新增系统用户激活
     *
     * @param sysUserActivate 系统用户激活信息
     * @return 结果
     */
    @Override
    public int insertSysUserActivate(SysUserActivate sysUserActivate)
    {
        return sysUserActivateMapper.insertSysUserActivate(sysUserActivate);
    }

    /**
     * 修改系统用户激活
     *
     * @param sysUserActivate 系统用户激活信息
     * @return 结果
     */
    @Override
    public int updateSysUserActivate(SysUserActivate sysUserActivate)
    {
        return sysUserActivateMapper.updateSysUserActivate(sysUserActivate);
    }


    /**
     * 删除系统用户激活
     *
     * @param tenantId 系统用户激活ID
     * @return 结果
     */
    public int deleteSysUserActivateById(String tenantId)
    {
        return sysUserActivateMapper.deleteSysUserActivateById( tenantId);
    };


    /**
     * 批量删除系统用户激活对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysUserActivateByIds(String[] tenantIds)
    {
        return sysUserActivateMapper.deleteSysUserActivateByIds( tenantIds);
    }

}
