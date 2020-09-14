package com.ztman.ztspark.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.common.core.util.Query;
import com.ztman.ztspark.entity.workorder.Maintenance;

/**
 * 工单维修信息  mapper层
 *
 * @author zxx
 * @date 2020-09-07 14:45:32
 */
public interface MaintenanceMapper extends BaseMapper<Maintenance> {
    /**
     * 查询工单维修信息信息
     *
     * @param id 工单维修信息ID
     * @return 工单维修信息信息
     */
    public Maintenance selectMaintenanceById(Long id);

    /**
     * 查询工单维修信息列表
     *
     * @param maintenance 工单维修信息信息
     * @return 工单维修信息集合
     */
    public List<Maintenance> selectMaintenanceList(Maintenance maintenance);

    /**
     * 模糊查询工单维修信息列表
     *
     * @param maintenance 工单维修信息信息
     * @return 工单维修信息集合
     */
    public List<Maintenance> selectMaintenanceListByLike(Maintenance maintenance);


    /**
     * 分页模糊查询工单维修信息列表
     *
     * @param maintenance 工单维修信息信息
     * @return 工单维修信息集合
     */
    public List<Maintenance> selectMaintenanceListByLike(Query query, Maintenance maintenance);


    /**
     * 新增工单维修信息
     *
     * @param maintenance 工单维修信息信息
     * @return 结果
     */
    public int insertMaintenance(Maintenance maintenance);

    /**
     * 修改工单维修信息
     *
     * @param maintenance 工单维修信息信息
     * @return 结果
     */
    public int updateMaintenance(Maintenance maintenance);

    /**
     * 删除工单维修信息
     *
     * @param id 工单维修信息ID
     * @return 结果
     */
    public int deleteMaintenanceById(Long id);

    /**
     * 批量删除工单维修信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMaintenanceByIds(String[] ids);



}
