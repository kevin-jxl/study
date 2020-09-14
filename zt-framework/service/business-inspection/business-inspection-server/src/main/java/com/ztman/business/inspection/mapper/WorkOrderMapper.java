package com.ztman.business.inspection.mapper;

import com.ztman.business.inspection.client.entity.WorkOrder;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.common.core.util.Query;

/**
 * 工单表  mapper层
 *
 * @author zxx
 * @date 2020-08-27 22:07:49
 */

public interface WorkOrderMapper extends BaseMapper<WorkOrder> {
    /**
     * 查询工单表信息
     *
     * @param id 工单表ID
     * @return 工单表信息
     */
    public WorkOrder selectWorkOrderById(String id);

    /**
     * 查询工单表列表
     *
     * @param workOrder 工单表信息
     * @return 工单表集合
     */
    public List<WorkOrder> selectWorkOrderList(WorkOrder workOrder);

    /**
     * 模糊查询工单表列表
     *
     * @param workOrder 工单表信息
     * @return 工单表集合
     */
    public List<WorkOrder> selectWorkOrderListByLike(WorkOrder workOrder);


    /**
     * 分页模糊查询工单表列表
     *
     * @param workOrder 工单表信息
     * @return 工单表集合
     */
    public List<WorkOrder> selectWorkOrderListByLike(Query query, WorkOrder workOrder);


    /**
     * 新增工单表
     *
     * @param workOrder 工单表信息
     * @return 结果
     */
    public int insertWorkOrder(WorkOrder workOrder);

    /**
     * 修改工单表
     *
     * @param workOrder 工单表信息
     * @return 结果
     */
    public int updateWorkOrder(WorkOrder workOrder);

    /**
     * 删除工单表
     *
     * @param id 工单表ID
     * @return 结果
     */
    public int deleteWorkOrderById(String id);

    /**
     * 批量删除工单表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWorkOrderByIds(String[] ids);



}
