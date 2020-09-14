package com.ztman.business.inspection.mapper;

import com.ztman.business.inspection.client.entity.SysTenant;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.common.core.util.Query;

/**
 * 租户  mapper层
 *
 * @author zxx
 * @date 2020-08-29 10:31:51
 */
public interface SysTenantMapper extends BaseMapper<SysTenant> {
    /**
     * 查询租户信息
     *
     * @param tenantId 租户ID
     * @return 租户信息
     */
    public SysTenant selectSysTenantById(Integer tenantId);

    /**
     * 查询租户列表
     *
     * @param sysTenant 租户信息
     * @return 租户集合
     */
    public List<SysTenant> selectSysTenantList(SysTenant sysTenant);

    /**
     * 模糊查询租户列表
     *
     * @param sysTenant 租户信息
     * @return 租户集合
     */
    public List<SysTenant> selectSysTenantListByLike(SysTenant sysTenant);


    /**
     * 分页模糊查询租户列表
     *
     * @param sysTenant 租户信息
     * @return 租户集合
     */
    public List<SysTenant> selectSysTenantListByLike(Query query, SysTenant sysTenant);


    /**
     * 新增租户
     *
     * @param sysTenant 租户信息
     * @return 结果
     */
    public int insertSysTenant(SysTenant sysTenant);

    /**
     * 修改租户
     *
     * @param sysTenant 租户信息
     * @return 结果
     */
    public int updateSysTenant(SysTenant sysTenant);

    /**
     * 删除租户
     *
     * @param tenantId 租户ID
     * @return 结果
     */
    public int deleteSysTenantById(Integer tenantId);

    /**
     * 批量删除租户
     *
     * @param tenantIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysTenantByIds(String[] tenantIds);



}
