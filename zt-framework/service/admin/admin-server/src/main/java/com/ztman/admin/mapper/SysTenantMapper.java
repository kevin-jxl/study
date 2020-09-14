package com.ztman.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.dto.SysTenantDTO;
import com.ztman.admin.api.entity.SysTenant;
import com.ztman.admin.api.vo.OrganTenantVO;
import com.ztman.common.core.util.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *   mapper层
 *
 * @author ztman code generator
 * @date 2020-08-26 10:02:33
 */
public interface SysTenantMapper extends BaseMapper<SysTenant> {
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
     * 模糊查询列表
     *
     * @param sysTenant 信息
     * @return 集合
     */
    public List<SysTenant> selectSysTenantListByLike(SysTenant sysTenant);


    /**
     * 分页模糊查询列表
     *
     * @param sysTenant 信息
     * @return 集合
     */
    public List<SysTenantDTO> selectSysTenantListByLike(Query query, SysTenantDTO sysTenant);


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


    List<OrganTenantVO> selectOrgan(@Param("tenantId") Integer tenantId);

    SysTenant findSysTenantByempno(SysTenant sysTenant);
}
