package com.ztman.ztspark.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.ztspark.entity.workorder.WorkOrderProcess;
import com.ztman.ztspark.entity.workorder.dto.WorkOrderProcessDTO;
import com.ztman.ztspark.mapper.WorkOrderProcessMapper;
import com.ztman.ztspark.service.WorkOrderProcessService;

@Service("workOrderProcessService")
public class WorkOrderProcessServiceImpl extends ServiceImpl<WorkOrderProcessMapper, WorkOrderProcess>
    implements WorkOrderProcessService {

    @Autowired
    private WorkOrderProcessMapper workOrderProcessMapper;

    @Override
    public List<WorkOrderProcessDTO> workOrderProcess(String workOrderId) {
        // TODO Auto-generated method stub
        List<WorkOrderProcessDTO> workOrderProcessDTOs = null;
        WorkOrderProcess workOrderProcess = new WorkOrderProcess();
        workOrderProcess.setWorkOrderId(workOrderId);
        List<WorkOrderProcess> workOrderProcesss = workOrderProcessMapper.selectWorkOrderProcessList(workOrderProcess);
        if (null == workOrderProcesss) {
            return workOrderProcessDTOs;
        }
        workOrderProcessDTOs = new ArrayList<WorkOrderProcessDTO>();
        for (WorkOrderProcess item : workOrderProcesss) {
            workOrderProcessDTOs.add(
                new WorkOrderProcessDTO(item.getTag(), item.getTagName(), item.getJoinName(), item.getOperationTime()));
        }
        return workOrderProcessDTOs;
    }

}
