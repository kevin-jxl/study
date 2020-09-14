package com.ztman.ztspark.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.ztspark.entity.workorder.Attachment;
import com.ztman.ztspark.entity.workorder.dto.AttachmentDTO;
import com.ztman.ztspark.mapper.AttachmentMapper;
import com.ztman.ztspark.service.AttachmentService;

@Service("attachmentService")
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements AttachmentService {

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    public List<AttachmentDTO> workOrderAttachment(String groupId) {
        // TODO Auto-generated method stub
        List<AttachmentDTO> attachmentDTOs = null;
        Attachment attachment = new Attachment();
        attachment.setGroupId(groupId);

        List<Attachment> attachments = attachmentMapper.selectAttachmentList(attachment);
        if (null == attachments) {
            return attachmentDTOs;
        }
        attachmentDTOs = new ArrayList<AttachmentDTO>();
        for (Attachment item : attachments) {

            attachmentDTOs.add(new AttachmentDTO(item.getId(), item.getPath(), item.getType()));
        }
        return attachmentDTOs;
    }

}
