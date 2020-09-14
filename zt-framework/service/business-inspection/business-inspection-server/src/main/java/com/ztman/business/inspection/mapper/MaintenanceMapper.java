package com.ztman.business.inspection.mapper;

import com.ztman.business.inspection.client.entity.Maintenance;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.common.core.util.Query;

/**
 * 工单维修表  mapper层
 *
 * @author zxx
 * @date 2020-08-29 10:34:49
 */
public interface MaintenanceMapper extends BaseMapper<Maintenance> {
    /**
     * 查询工单维修表信息
     *
     * @param id 工单维修表ID
     * @return 工单维修表信息
     */
    public Maintenance selectMaintenanceById(Long id);

    /**
     * 查询工单维修表列表
     *
     * @param maintenance 工单维修表信息
     * @return 工单维修表集合
     */
    public List<Maintenance> selectMaintenanceList(Maintenance maintenance);

    /**
     * 模糊查询工单维修表列表
     *
     * @param maintenance 工单维修表信息
     * @return 工单维修表集合
     */
    public List<Maintenance> selectMaintenanceListByLike(Maintenance maintenance);


    /**
     * 分页模糊查询工单维修表列表
     *
     * @param maintenance 工单维修表信息
     * @return 工单维修表集合
     */
    public List<Maintenance> selectMaintenanceListByLike(Query query, Maintenance maintenance);


    /**
     * 新增工单维修表
     *
     * @param maintenance 工单维修表信息
     * @return 结果
     */
    public int insertMaintenance(Maintenance maintenance);

    /**
     * 修改工单维修表
     *
     * @param maintenance 工单维修表信息
     * @return 结果
     */
    public int updateMaintenance(Maintenance maintenance);

    /**
     * 删除工单维修表
     *
     * @param id 工单维修表ID
     * @return 结果
     */
    public int deleteMaintenanceById(Long id);

    /**
     * 批量删除工单维修表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMaintenanceByIds(String[] ids);



}
