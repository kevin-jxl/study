package com.ztman.ztspark.service;

import com.baomidou.mybatisplus.service.IService;
import com.ztman.ztspark.entity.workorder.WorkOrderAssign;
import com.ztman.ztspark.entity.workorder.dto.WorkOrderAssignDTO;
/**
 * 工单派遣信息 服务接口层
 *
 * @author zxx
 * @date 2020-09-07 14:38:19
 */
public interface WorkOrderAssignService extends IService<WorkOrderAssign> {
     

    public boolean insertAssign(String realName ,WorkOrderAssignDTO workOrderAssignDTO);

    public boolean assigned(String workOrderId);

}

