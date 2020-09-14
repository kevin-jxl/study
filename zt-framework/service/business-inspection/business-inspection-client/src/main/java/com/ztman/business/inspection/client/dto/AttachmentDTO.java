package com.ztman.business.inspection.client.dto;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotations.TableId;

import lombok.Data;

@Data
public class AttachmentDTO {

	private static final long serialVersionUID = 1L;

	/**
	 * 文件ID
	 */
	@TableId
	private Long id;
	/**
	 * 文件组编码
	 */
	private String groupId; 
	/**
	 * 资源url
	 */
	private String url; 
	/**
	 * 文件类型(1.语音 2.图片 3.视频 4.文件)
	 */
	private String type; 
	/**
	 * 备注
	 */
	private String note;
}
