package com.ztman.ztspark.service;

import java.util.HashMap;
import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.ztman.ztspark.entity.workorder.WorkOrder;
import com.ztman.ztspark.entity.workorder.dto.WorkOrderDTO;
import com.ztman.ztspark.entity.workorder.vo.WorkOrderAssignVO;
import com.ztman.ztspark.entity.workorder.vo.WorkOrderListVO;

 
/**
 * 维修工单 服务接口层
 *
 * @author zxx
 * @date 2020-09-07 14:56:28
 */
public interface WorkOrderService extends IService<WorkOrder> {
    

    public boolean workOrderDelete(int userId, String orderId);

    public WorkOrderDTO workOrderDetail(String id);

    public WorkOrderListVO workOrderList(String tenantId, int page, int size, String searchKey, String type,
        String park);

    public WorkOrderAssignVO workOrderAssignList(String tenantId, int page, int size, String searchKey, String date,
        String type);

    public List<HashMap<String, String>> workOrderStatistics(String tenantId);
 
}

