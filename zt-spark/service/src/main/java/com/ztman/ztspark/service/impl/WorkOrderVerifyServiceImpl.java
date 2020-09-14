package com.ztman.ztspark.service.impl;
  

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.ztspark.entity.workorder.Attachment;
import com.ztman.ztspark.entity.workorder.WorkOrder;
import com.ztman.ztspark.entity.workorder.WorkOrderProcess;
import com.ztman.ztspark.entity.workorder.WorkOrderSupervise;
import com.ztman.ztspark.entity.workorder.WorkOrderVerify;
import com.ztman.ztspark.entity.workorder.dto.WorkOrderVerifyDTO;
import com.ztman.ztspark.entity.workorder.vo.WorkOrderVerifyVO;
import com.ztman.ztspark.mapper.AttachmentMapper;
import com.ztman.ztspark.mapper.WorkOrderProcessMapper;
import com.ztman.ztspark.mapper.WorkOrderVerifyMapper;
import com.ztman.ztspark.service.WorkOrderVerifyService;

@Service("workOrderVerifyService")
public class WorkOrderVerifyServiceImpl extends ServiceImpl<WorkOrderVerifyMapper, WorkOrderVerify>
    implements WorkOrderVerifyService {

    @Autowired
    private WorkOrderVerifyMapper workOrderVerifyMapper;

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Autowired
    private WorkOrderProcessMapper workOrderProcessMapper;
    
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertVerify(WorkOrderVerifyDTO workOrderVerifyDTO,String realName) {
        // TODO Auto-generated method stub
        WorkOrderVerify workOrderVerify = new WorkOrderVerify();
        workOrderVerify.setCreateTime(LocalDateTime.now());
        workOrderVerify.setDescription(workOrderVerifyDTO.getDescription());
        workOrderVerify.setIsAchieve(workOrderVerifyDTO.getIsAchieve());
        workOrderVerify.setUserId(workOrderVerifyDTO.getUserId());
        workOrderVerify.setWorkOrderId(workOrderVerifyDTO.getWorkOrderId());
        //增加处理过程
        WorkOrderProcess workOrderProcess = new WorkOrderProcess();
        workOrderProcess.setJoinName(realName); 
        workOrderProcess.setJoinUserId(workOrderVerifyDTO.getUserId());
        workOrderProcess.setOperationTime(LocalDateTime.now());
        workOrderProcess.setTag("5");
        workOrderProcess.setTagName("已关闭");
        workOrderProcess.setWorkOrderId(workOrderVerifyDTO.getWorkOrderId());
        
        workOrderProcessMapper.insertWorkOrderProcess(workOrderProcess);
        
        return workOrderVerifyMapper.insertWorkOrderVerify(workOrderVerify) > 0;
    }

    @Override
    public WorkOrderVerifyVO workOrderVerifyDetail(String workOrderId) {
        // TODO Auto-generated method stub
        WorkOrderVerifyVO workOrderVerifyVO = null;
        WorkOrderVerify workOrderVerify = new WorkOrderVerify();
        workOrderVerify.setWorkOrderId(workOrderId);
        workOrderVerify = workOrderVerifyMapper.selectOne(workOrderVerify);
        if (null == workOrderVerify) {
            return workOrderVerifyVO;
        }
        // 查询工单过程信息
        WorkOrderProcess workOrderProcess = new WorkOrderProcess();
        workOrderProcess.setWorkOrderId(workOrderId);
        workOrderProcess.setTag("5");
        workOrderProcess = workOrderProcessMapper.selectOne(workOrderProcess);

        workOrderVerifyVO = new WorkOrderVerifyVO();
        workOrderVerifyVO.setDescription(workOrderVerify.getDescription());
        workOrderVerifyVO.setIsAchieve(workOrderVerify.getIsAchieve());
        workOrderVerifyVO.setOperationTime(workOrderProcess.getOperationTime());
        workOrderVerifyVO.setPersonal(workOrderProcess.getJoinName());
        workOrderVerifyVO.setWorkOrderId(workOrderId);

        // 获取附件列表
        WorkOrder workOrder = new WorkOrder();
        workOrder.setId(workOrderId);
        workOrder.selectById();

        if (null != workOrder.getAttachmentGroupId()) {
            Attachment attachment = new Attachment();
            attachment.setGroupId(workOrder.getAttachmentGroupId());
            List<Attachment> attachments = attachmentMapper.selectAttachmentList(attachment);
            List<WorkOrderVerifyVO.MaintenanceAttachment> ma_attachments =
                new ArrayList<WorkOrderVerifyVO.MaintenanceAttachment>();
            for (Attachment item : attachments) {
                ma_attachments
                    .add(workOrderVerifyVO.new MaintenanceAttachment(item.getId(), item.getPath(), item.getType()));
            }
            workOrderVerifyVO.setAttachments(ma_attachments);
        }
        return workOrderVerifyVO;
    }

    @Override
    public boolean verified(String workOrderId) {
        // TODO Auto-generated method stub
        WorkOrderVerify workOrderVerify = new WorkOrderVerify();
        workOrderVerify.setWorkOrderId(workOrderId);
        workOrderVerify = workOrderVerifyMapper.selectOne(workOrderVerify);
         return null == workOrderVerify;
    }

    

}
