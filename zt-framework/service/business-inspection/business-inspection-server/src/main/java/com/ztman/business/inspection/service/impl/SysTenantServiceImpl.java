package com.ztman.business.inspection.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import com.ztman.common.core.util.Query;
import com.ztman.business.inspection.mapper.SysTenantMapper;
import com.ztman.business.inspection.client.entity.SysTenant;
import com.ztman.business.inspection.service.SysTenantService;


@Service("sysTenantService")
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements SysTenantService {

    @Autowired
    private SysTenantMapper sysTenantMapper;

    /**
     * 查询租户信息
     *
     * @param tenantId 租户ID
     * @return 租户信息
     */
    @Override
    public SysTenant selectSysTenantById(Integer tenantId)
    {
        return sysTenantMapper.selectSysTenantById(tenantId);
    }

    /**
     * 查询租户列表
     *
     * @param sysTenant 租户信息
     * @return 租户集合
     */
    @Override
    public List<SysTenant> selectSysTenantList(SysTenant sysTenant)
    {
        return sysTenantMapper.selectSysTenantList(sysTenant);
    }


    /**
     * 分页模糊查询租户列表
     * @return 租户集合
     */
    @Override
    public Page selectSysTenantListByLike(Query query)
    {
        SysTenant sysTenant =  BeanUtil.mapToBean(query.getCondition(), SysTenant.class,false);
        query.setRecords(sysTenantMapper.selectSysTenantListByLike(query,sysTenant));
        return query;
    }

    /**
     * 新增租户
     *
     * @param sysTenant 租户信息
     * @return 结果
     */
    @Override
    public int insertSysTenant(SysTenant sysTenant)
    {
        return sysTenantMapper.insertSysTenant(sysTenant);
    }

    /**
     * 修改租户
     *
     * @param sysTenant 租户信息
     * @return 结果
     */
    @Override
    public int updateSysTenant(SysTenant sysTenant)
    {
        return sysTenantMapper.updateSysTenant(sysTenant);
    }


    /**
     * 删除租户
     *
     * @param tenantId 租户ID
     * @return 结果
     */
    public int deleteSysTenantById(Integer tenantId)
    {
        return sysTenantMapper.deleteSysTenantById( tenantId);
    };


    /**
     * 批量删除租户对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysTenantByIds(String[] tenantIds)
    {
        return sysTenantMapper.deleteSysTenantByIds( tenantIds);
    }

}
