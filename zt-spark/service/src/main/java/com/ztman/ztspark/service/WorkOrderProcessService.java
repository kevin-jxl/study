package com.ztman.ztspark.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.ztman.ztspark.entity.workorder.WorkOrderProcess;
import com.ztman.ztspark.entity.workorder.dto.WorkOrderProcessDTO;
/**
 * 工单办理经过表 服务接口层
 *
 * @author zxx
 * @date 2020-09-07 15:02:57
 */
public interface WorkOrderProcessService extends IService<WorkOrderProcess> {
    

    public List<WorkOrderProcessDTO> workOrderProcess(String workOrderId);

}

