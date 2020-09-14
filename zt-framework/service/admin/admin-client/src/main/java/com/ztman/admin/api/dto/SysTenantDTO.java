package com.ztman.admin.api.dto;

import com.ztman.admin.api.entity.SysTenant;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 *
 * @author ztman code generator
 * @date 2020-08-26 10:02:33
 */
@Data
public class SysTenantDTO extends SysTenant implements Serializable  {
	private static final long serialVersionUID = 1L;

	private String adminName;
	private String createName;
	private String areaName;


}
