package com.ztman.ztspark.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.ztspark.entity.workorder.WorkOrder;
import com.ztman.ztspark.entity.workorder.vo.WorkOrderListVO.WorkOrderVO;

/**
 * 维修工单  mapper层
 *
 * @author zxx
 * @date 2020-09-07 14:56:28
 */
public interface WorkOrderMapper extends BaseMapper<WorkOrder> {
    /**
     * 查询维修工单信息
     *
     * @param id 维修工单ID
     * @return 维修工单信息
     */
    public WorkOrder selectWorkOrderById(String id);

    /**
     * 查询维修工单列表
     *
     * @param workOrder 维修工单信息
     * @return 维修工单集合
     */
    public List<WorkOrder> selectWorkOrderList(WorkOrder workOrder);

    

    /**
     * 修改维修工单
     *
     * @param workOrder 维修工单信息
     * @return 结果
     */
    public int updateWorkOrder(WorkOrder workOrder);
 

    public List<WorkOrderVO> workOrderList(@Param("tenantId") String tenantId, @Param("page") int page, @Param("size") int size, @Param("searchKey") String searchKey, @Param("type") String type, @Param("park") String park);

    public int workOrderListTotal(@Param("tenantId") String tenantId,  @Param("searchKey") String searchKey, @Param("type") String type, @Param("park") String park);

    public List<HashMap<String, String>> statistics(@Param("tenantId") String tenantId);

    public int workOrderAssignListTotal(@Param("tenantId") String tenantId, @Param("searchKey") String searchKey, @Param("type") String type,@Param("date") String date);

    public List<com.ztman.ztspark.entity.workorder.vo.WorkOrderAssignVO.WorkOrderVO>
        workOrderAssignList(@Param("tenantId") String tenantId,@Param("page") int page,@Param("size") int size,@Param("searchKey") String searchKey,@Param("type") String type,@Param("date") String date);

    

}
