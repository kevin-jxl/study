package com.ztman.ztspark.service;

import com.baomidou.mybatisplus.service.IService;
import com.ztman.ztspark.entity.workorder.WorkOrderVerify;
import com.ztman.ztspark.entity.workorder.dto.WorkOrderVerifyDTO;
import com.ztman.ztspark.entity.workorder.vo.WorkOrderVerifyVO;
/**
 * 工单核查表 服务接口层
 *
 * @author zxx
 * @date 2020-09-07 14:37:25
 */
public interface WorkOrderVerifyService extends IService<WorkOrderVerify> {
  

    public boolean insertVerify(WorkOrderVerifyDTO workOrderVerifyDTO,String realName);

    public WorkOrderVerifyVO workOrderVerifyDetail(String workOrderId);

    public boolean verified(String workOrderId);

   

}

