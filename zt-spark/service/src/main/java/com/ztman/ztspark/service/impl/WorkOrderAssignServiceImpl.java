package com.ztman.ztspark.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.ztspark.entity.workorder.WorkOrder;
import com.ztman.ztspark.entity.workorder.WorkOrderAssign;
import com.ztman.ztspark.entity.workorder.WorkOrderProcess;
import com.ztman.ztspark.entity.workorder.dto.WorkOrderAssignDTO;
import com.ztman.ztspark.mapper.WorkOrderAssignMapper;
import com.ztman.ztspark.mapper.WorkOrderMapper;
import com.ztman.ztspark.mapper.WorkOrderProcessMapper;
import com.ztman.ztspark.service.WorkOrderAssignService;


@Service("workOrderAssignService")
public class WorkOrderAssignServiceImpl extends ServiceImpl<WorkOrderAssignMapper, WorkOrderAssign> implements WorkOrderAssignService {

    @Autowired
    private WorkOrderAssignMapper workOrderAssignMapper;
    @Autowired
    private WorkOrderProcessMapper workOrderProcessMapper;
    @Autowired
    private WorkOrderMapper workOrderMapper;
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertAssign(String realName ,WorkOrderAssignDTO workOrderAssignDTO) {
        // TODO Auto-generated method stub
        WorkOrderAssign workOrderAssign = new WorkOrderAssign();
        workOrderAssign.setCreateTime(LocalDateTime.now());
        workOrderAssign.setContact(workOrderAssignDTO.getContact());
        workOrderAssign.setEmName(workOrderAssignDTO.getEmName());
        workOrderAssign.setEmNo(workOrderAssignDTO.getEmNo());
        workOrderAssign.setEmUserId(workOrderAssignDTO.getEmUserId());
        workOrderAssign.setPmin(workOrderAssignDTO.getPmin());
        workOrderAssign.setReplenish(workOrderAssignDTO.getReplenish());
        workOrderAssign.setAssignUserId(workOrderAssignDTO.getAssignUserId());
        workOrderAssign.setWorkOrderId(workOrderAssignDTO.getWorkOrderId()); 
       boolean flag = workOrderAssignMapper.insertWorkOrderAssign(workOrderAssign) > 0;
        if(flag) {
            //添加工单过程信息
            WorkOrderProcess workOrderProcess = new WorkOrderProcess();
            workOrderProcess.setJoinName(realName);
            workOrderProcess.setJoinUserId(workOrderAssignDTO.getAssignUserId());
            workOrderProcess.setOperationTime(LocalDateTime.now());
            workOrderProcess.setTag("1");
            workOrderProcess.setTagName("已派遣");
            workOrderProcess.setWorkOrderId(workOrderAssignDTO.getWorkOrderId());
            flag = workOrderProcessMapper.insertWorkOrderProcess(workOrderProcess) >0;
            //更新主工单上的被派遣人信息
            WorkOrder workOrder = new WorkOrder();
            workOrder.setId(workOrderAssignDTO.getWorkOrderId());
            workOrder.setAssignUserId(workOrderAssignDTO.getEmUserId());
            workOrder.setAssignUserName(workOrderAssignDTO.getEmName()); 
            workOrderMapper.updateWorkOrder(workOrder);
        } 
        return flag;
    }

    @Override
    public boolean assigned(String workOrderId) {
        // TODO Auto-generated method stub
        WorkOrderAssign workOrderAssign = new WorkOrderAssign();
        workOrderAssign.setWorkOrderId(workOrderId);
        workOrderAssign = workOrderAssignMapper.selectOne(workOrderAssign);
         return null == workOrderAssign;
    }

     

}
