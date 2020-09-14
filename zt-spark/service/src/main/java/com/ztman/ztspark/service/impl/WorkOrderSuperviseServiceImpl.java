package com.ztman.ztspark.service.impl;
 
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.ztspark.entity.workorder.WorkOrderAssign;
import com.ztman.ztspark.entity.workorder.WorkOrderSupervise;
import com.ztman.ztspark.entity.workorder.dto.WorkOrderSuperviseDTO;
import com.ztman.ztspark.mapper.WorkOrderSuperviseMapper;
import com.ztman.ztspark.service.WorkOrderSuperviseService;

@Service("workOrderSuperviseService")
public class WorkOrderSuperviseServiceImpl extends ServiceImpl<WorkOrderSuperviseMapper, WorkOrderSupervise>
    implements WorkOrderSuperviseService {

    @Autowired
    private WorkOrderSuperviseMapper workOrderSuperviseMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertSupervise(WorkOrderSuperviseDTO workOrderSuperviseDTO) {
        // TODO Auto-generated method stub
        WorkOrderSupervise workOrderSupervise = new WorkOrderSupervise();
        workOrderSupervise.setCreateTime(LocalDateTime.now());
        workOrderSupervise.setDescription(workOrderSuperviseDTO.getDescription());
        workOrderSupervise.setPmin(workOrderSuperviseDTO.getPmin());
        workOrderSupervise.setSmsReminder(workOrderSuperviseDTO.getSmsReminder());
        workOrderSupervise.setReasons(workOrderSuperviseDTO.getReasons());
        if (workOrderSuperviseDTO.getSmsReminder().equals("1")) {
            String phone =
                workOrderSuperviseMapper.getSmsReminderPhoneByWorkOrderId(workOrderSuperviseDTO.getWorkOrderId());
            workOrderSupervise.setSmsReminderPhone(phone);

        } 
        workOrderSupervise.setSuperviseUserId(workOrderSuperviseDTO.getSuperviseUserId());
        workOrderSupervise.setWorkOrderId(workOrderSuperviseDTO.getWorkOrderId());
        boolean flag = workOrderSuperviseMapper.insertWorkOrderSupervise(workOrderSupervise) > 0;
        if (flag && null != workOrderSupervise) {
            // 发送手机短信提醒
            System.out.print("发送短信~");

        }
        return flag;
    }

    @Override
    public boolean supervised(String workOrderId) {
        // TODO Auto-generated method stub
        WorkOrderSupervise workOrderSupervise = new WorkOrderSupervise();
        workOrderSupervise.setWorkOrderId(workOrderId);
        workOrderSupervise = workOrderSuperviseMapper.selectOne(workOrderSupervise);
         return null == workOrderSupervise;
    } 

}
