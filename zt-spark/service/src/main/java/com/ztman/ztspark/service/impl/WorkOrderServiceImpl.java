package com.ztman.ztspark.service.impl;
 

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.ztspark.entity.workorder.WorkOrder;
import com.ztman.ztspark.entity.workorder.WorkOrderDelete;
import com.ztman.ztspark.entity.workorder.dto.WorkOrderDTO;
import com.ztman.ztspark.entity.workorder.vo.WorkOrderAssignVO;
import com.ztman.ztspark.entity.workorder.vo.WorkOrderListVO;
import com.ztman.ztspark.mapper.WorkOrderDeleteMapper;
import com.ztman.ztspark.mapper.WorkOrderMapper;
import com.ztman.ztspark.service.WorkOrderService;

@Service("workOrderService")
public class WorkOrderServiceImpl extends ServiceImpl<WorkOrderMapper, WorkOrder> implements WorkOrderService {

    @Autowired
    private WorkOrderMapper workOrderMapper;

    @Autowired
    WorkOrderDeleteMapper workOrderDeleteMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean workOrderDelete(int userId, String orderId) {
        // TODO Auto-generated method stub
        WorkOrder workOrder = new WorkOrder();
        workOrder.setId(orderId);
        workOrder.setDelFlag("1");

        boolean flag = workOrder.update(new EntityWrapper<WorkOrder>().eq("id", orderId).eq("del_flag", "0"));// workOrderMapper.updateWorkOrder(workOrder)
                                                                                                              // > 0;
        if (flag) {
            WorkOrderDelete workOrderDelete = new WorkOrderDelete();
            workOrderDelete.setCreateTime(LocalDateTime.now());
            workOrderDelete.setDelUserId(userId + "");
            workOrderDelete.setWorkOrderId(orderId);

            flag = workOrderDeleteMapper.insertWorkOrderDelete(workOrderDelete) > 0;

        }
        return flag;
    }

    @Override
    public WorkOrderDTO workOrderDetail(String id) {
        // TODO Auto-generated method stub
        WorkOrderDTO workOrderDTO = null;
        WorkOrder workOrder =
            new WorkOrder().selectOne(new EntityWrapper<WorkOrder>().eq("del_flag", '0').eq("id", id));

        if (null != workOrder) {
            workOrderDTO = new WorkOrderDTO();
            workOrderDTO.setAttachmentGroupId(workOrder.getAttachmentGroupId());
            workOrderDTO.setDataSource(workOrder.getDataSource());
            workOrderDTO.setEquipmentId(workOrder.getEquipmentId());
            workOrderDTO.setEquipmentName(workOrder.getEquipmentName());
            workOrderDTO.setExigencyStatus(workOrder.getExigencyStatus());
            workOrderDTO.setId(id);
            workOrderDTO.setProblemDesc(workOrder.getProblemDesc());
            workOrderDTO.setProblemLevel(workOrder.getProblemLevel());
            workOrderDTO.setProblemType(workOrder.getProblemType());
            workOrderDTO.setReportAddr(workOrder.getReportAddr());
            workOrderDTO.setReportLat(workOrder.getReportLat());
            workOrderDTO.setReportLng(workOrder.getReportLng());
            workOrderDTO.setReportTime(workOrder.getReportTime());
            workOrderDTO.setReportPersonId(workOrder.getReportPersonId());

        }
        return workOrderDTO;
    }

    @Override
    public WorkOrderListVO workOrderList(String tenantId, int page, int size, String searchKey, String type,
        String park) {
        // TODO Auto-generated method stub
        WorkOrderListVO workOrderListVO = null;
        System.out.println(
            "[{" + tenantId + "},{" + page + "},{" + size + "},{" + searchKey + "},{" + type + "},{" + park + "}]");
        page = ((page == 0 ? 1 : page) - 1) * size;
        List<WorkOrderListVO.WorkOrderVO> workOrderVO =
            workOrderMapper.workOrderList(tenantId, page, size, searchKey, type, park);
        if (null != workOrderVO) {
            workOrderListVO = new WorkOrderListVO();
            workOrderListVO.setTotal(workOrderMapper.workOrderListTotal(tenantId, searchKey, type, park));
            workOrderListVO.setRecords(workOrderVO);
        }

        return workOrderListVO;
    }

    @Override
    public WorkOrderAssignVO workOrderAssignList(String tenantId, int page, int size, String searchKey, String date,
        String type) {
        // TODO Auto-generated method stub
        WorkOrderAssignVO workOrderAssignVO = null;
        System.out.println(
            "[{" + tenantId + "},{" + page + "},{" + size + "},{" + searchKey + "},{" + type + "},{" + date + "}]");
        page = ((page == 0 ? 1 : page) - 1) * size;
        List<WorkOrderAssignVO.WorkOrderVO> workOrderVO =
            workOrderMapper.workOrderAssignList(tenantId, page, size, searchKey, type, date);
        if (null != workOrderVO) {
            workOrderAssignVO = new WorkOrderAssignVO();
            workOrderAssignVO.setTotal(workOrderMapper.workOrderAssignListTotal(tenantId, searchKey, type, date));
            workOrderAssignVO.setRecords(workOrderVO);
            
//            workOrderAssignVO.setStatistics(workOrderMapper.statistics(tenantId));
            
        }

        return workOrderAssignVO;
    }

    @Override
    public List<HashMap<String, String>> workOrderStatistics(String tenantId) {
        // TODO Auto-generated method stub 
         return workOrderMapper.statistics(tenantId);
    }

     

}
