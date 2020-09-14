package com.ztman.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.dto.SysTenantDTO;
import com.ztman.admin.api.entity.SysTenant;
import com.ztman.admin.api.vo.OrganVO;
import com.ztman.common.core.util.Query;

import java.util.List;

/**
 *  服务接口层
 *
 * @author ztman code generator
 * @date 2020-08-26 10:02:33
 */
public interface SysTenantService extends IService<SysTenant> {
    /**
     * 查询信息
     *
     * @param tenantId ID
     * @return 信息
     */
    public SysTenantDTO selectSysTenantById(Integer tenantId);

    /**
     * 查询列表
     *
     * @param sysTenant 信息
     * @return 集合
     */
    public List<SysTenant> selectSysTenantList(SysTenant sysTenant);


    /**
     * 分页模糊查询列表
     * @return 集合
     */
    public Page selectSysTenantListByLike(Query query);



    /**
     * 新增
     *
     * @param sysTenant 信息
     * @return 结果
     */
    public int insertSysTenant(SysTenant sysTenant);

    /**
     * 修改
     *
     * @param sysTenant 信息
     * @return 结果
     */
    public int updateSysTenant(SysTenant sysTenant);

    /**
     * 删除
     *
     * @param tenantId ID
     * @return 结果
     */
    public int deleteSysTenantById(Integer tenantId);

    /**
     * 批量删除
     *
     * @param tenantIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysTenantByIds(String[] tenantIds);

    List<OrganVO> selectOrgan(Integer tenantId);

    SysTenant findSysTenantByempno(SysTenant sysTenant);
}

