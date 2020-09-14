package com.ztman.ztspark.service;

import com.baomidou.mybatisplus.service.IService;
import com.ztman.ztspark.entity.workorder.WorkOrderSupervise;
import com.ztman.ztspark.entity.workorder.dto.WorkOrderSuperviseDTO;
/**
 * 工单督办表 服务接口层
 *
 * @author zxx
 * @date 2020-09-07 14:38:52
 */
public interface WorkOrderSuperviseService extends IService<WorkOrderSupervise> {
   

    public boolean insertSupervise(WorkOrderSuperviseDTO workOrderSuperviseDTO);

    public boolean supervised(String workOrderId); 

}

