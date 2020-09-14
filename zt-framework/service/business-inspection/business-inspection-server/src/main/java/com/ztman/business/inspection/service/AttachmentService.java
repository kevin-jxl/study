package com.ztman.business.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import com.ztman.business.inspection.client.dto.AttachmentDTO;
import com.ztman.business.inspection.client.entity.Attachment;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
 
/**
 * 附件 服务接口层
 *
 * @author zxx
 * @date 2020-08-29 11:05:32
 */
public interface AttachmentService extends IService<Attachment> { 
    /**
     * 删除附件
     *
     * @param id 附件ID
     * @return 结果
     */
    public int deleteAttachmentById(Long id);
 
 
	public AttachmentDTO save(MultipartFile file, AttachmentDTO attachmentDTO, int id);


	public Boolean deleteBy(Long id, int userId);


	public List<AttachmentDTO> listBy(String groupId, Integer userId);

}

