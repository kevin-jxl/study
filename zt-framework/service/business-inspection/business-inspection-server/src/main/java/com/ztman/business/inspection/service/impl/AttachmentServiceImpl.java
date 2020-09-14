package com.ztman.business.inspection.service.impl;
 
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate; 
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper; 
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
 
import com.ztman.business.inspection.mapper.AttachmentMapper;
import com.ztman.business.inspection.client.dto.AttachmentDTO;
import com.ztman.business.inspection.client.entity.Attachment;
import com.ztman.business.inspection.service.AttachmentService;


@Service("attachmentService")
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements AttachmentService {

    @Autowired
    private AttachmentMapper attachmentMapper;

   //附件对外域名地址
    @Value("${file.domain}")
    private  String fileDomainUrl;

    //附件保存地址
    @Value("${file.path}")
    private  String filePath;

    /**
     * 删除附件
     *
     * @param id 附件ID
     * @return 结果
     */
    public int deleteAttachmentById(Long id)
    {
        return attachmentMapper.deleteAttachmentById( id);
    }



	@Override
	@Transactional(rollbackFor = {Exception.class,IOException.class,IllegalStateException.class})
	public AttachmentDTO save(MultipartFile file, AttachmentDTO attachmentDTO, int userId) {
		// TODO Auto-generated method stub
		AttachmentDTO _attachmentDTO = null;
    	InputStream in = null; 
        if (file.isEmpty()) { 
            return null;
        }
        //获取文件后缀
        String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        
        //文件新名称
        String newName=UUID.randomUUID() +"."+fileType;
       
        //生成年月日文件夹
        String subDir =  LocalDate.now().toEpochDay()+"";
        File dir = new File(filePath + subDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        //相对路径【保存到数据库中】
        String fileUrl = subDir + "/" + newName;
        File tarFile = new File(dir.getPath() + "/" + newName);
        Attachment attachment = new Attachment();
        attachment.setCreateTime(java.time.LocalDateTime.now());
        attachment.setGroupId(attachmentDTO.getGroupId());//UUID.randomUUID().toString().replaceAll("-", ""));
        attachment.setNote(attachmentDTO.getNote());
        attachment.setSuffix(fileType);
        attachment.setType(attachmentDTO.getType());
        attachment.setUpUserId(userId+"");
        attachment.setTenantId("");
        attachment.setPath(fileUrl);
        
        if(attachmentMapper.insertAttachment(attachment)>0) {
        	_attachmentDTO = new AttachmentDTO();
        	_attachmentDTO.setGroupId(attachment.getGroupId());
        	_attachmentDTO.setId(attachment.getId());
        	_attachmentDTO.setType(attachment.getType());
        	_attachmentDTO.setUrl(attachment.getPath());
        	_attachmentDTO.setNote(attachment.getNote()); 
        }
        
        try {
			file.transferTo(tarFile);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if(true) {
        	
        }
		return _attachmentDTO;
	}



	@Override
	public Boolean deleteBy(Long id, int userId) {
		// TODO Auto-generated method stub
		
		Attachment attachment =  new Attachment();
		attachment.setId(id);
		attachment.setUpUserId(userId+"");
		attachment = attachmentMapper.selectOne(attachment);
		if(null != attachment) {
			String filePath = "";
			//删除物理文件 
			File file = new File(filePath+attachment.getPath());
			if(file.exists()) {
				file.delete();
			}
			
			return attachmentMapper.deleteAttachmentById(id) > 0;
		}
		
		return false;
	}



	@Override
	public List<AttachmentDTO> listBy(String groupId, Integer userId) {
		// TODO Auto-generated method stub
		List<AttachmentDTO> attachmentDTOs = null;
		Attachment attachment = new Attachment();
		attachment.setUpUserId(userId+"");
		attachment.setGroupId(groupId);
		EntityWrapper<Attachment> wrapper = new EntityWrapper<>(attachment);
		List<Attachment> attachments = attachmentMapper.selectList(wrapper);
		if(null != attachments) {
			attachmentDTOs = new ArrayList<AttachmentDTO>();
			for (Attachment item : attachments) {
				AttachmentDTO attachmentDTO = new AttachmentDTO();
				attachmentDTO.setGroupId(groupId);
				attachmentDTO.setId(item.getId());
				attachmentDTO.setNote(item.getNote());
				attachmentDTO.setType(item.getType());
				attachmentDTO.setUrl(item.getPath());
				attachmentDTOs.add(attachmentDTO);
			}
		}
		return attachmentDTOs;
	}
  
     

}
