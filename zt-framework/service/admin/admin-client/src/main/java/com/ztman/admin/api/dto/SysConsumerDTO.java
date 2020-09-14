package com.ztman.admin.api.dto;

import com.ztman.admin.api.entity.SysConsumer;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户管理
 *
 * @author shuda
 * @date 2020-08-21 16:21:27
 */
@Data

public class SysConsumerDTO extends SysConsumer implements Serializable {
	private static final long serialVersionUID = 1L;
	private String companyName;
	private String organName;



}
