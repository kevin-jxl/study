package com.ztman.business.inspection.controller;


import com.ztman.business.inspection.client.dto.AttachmentDTO;
import com.ztman.business.inspection.client.entity.Attachment;
import com.ztman.business.inspection.service.AttachmentService;
import com.ztman.business.inspection.service.impl.UserDetailsImpl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.plugins.Page; 
import com.ztman.common.core.util.ExcelUtil;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import com.ztman.common.security.DTO.SecruityUser; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ztman.common.security.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 附件
 *
 * @author zxx
 * @date 2020-08-29 11:05:32
 */
@RestController
@RequestMapping("/attachment")
@Slf4j
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService; 
     
    @Autowired
    private UserDetailsImpl userDetailsImpl;
    /**
     * 上传文件
     * @param id
     * @return R
     */
    @PostMapping("/upload")
    public R upload(HttpServletRequest request) { 
    	AttachmentDTO attachmentDTO = new AttachmentDTO();
    	List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
    	attachmentDTO.setType(request.getParameter("type")); 
    	attachmentDTO.setGroupId(request.getParameter("groupId"));
    	if(files.size()==0) {
        	return new R<>(new Throwable("请选择文件"));
        } 
        attachmentDTO = attachmentService.save(files.get(0),attachmentDTO,userDetailsImpl.getUserDetail().getId());
        if(null == attachmentDTO) {
        	return new R<>(new Throwable("上传失败"));
        } 
        return new R<>(attachmentDTO);
    } 
    /**
     * 附件列表
     * @param groupId
     * @return R
     */
    @GetMapping("/{groupId}")
    public R list(@PathVariable()  String groupId) { 
        List<AttachmentDTO> attachmentDTOs = attachmentService.listBy(groupId,userDetailsImpl.getUserDetail().getId());
         
        return new R<>(attachmentDTOs);
    }
    /**
     * 删除
     * @param id
     * @return R
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable()  Long id) { 
        Boolean flg = attachmentService.deleteBy(id,userDetailsImpl.getUserDetail().getId());
        if(!flg) {
        	return new R<>(new Throwable("删除失败"));
        }
        return new R<>();
    }

 
}
