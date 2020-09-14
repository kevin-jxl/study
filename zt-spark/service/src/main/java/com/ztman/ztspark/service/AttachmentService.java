package com.ztman.ztspark.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.ztman.ztspark.entity.workorder.Attachment;
import com.ztman.ztspark.entity.workorder.dto.AttachmentDTO;
 
/**
 * 附件表 服务接口层
 *
 * @author zxx
 * @date 2020-09-07 14:46:30
 */
public interface AttachmentService extends IService<Attachment> {
    

    public List<AttachmentDTO> workOrderAttachment(String groupId);

}

