package com.ztman.admin.api.vo;

import com.ztman.admin.api.dto.SysTenantDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 *
 * @author  code generator
 * @date 2020-08-26 10:02:33
 */
@Data
public class SysTenantVo implements Serializable  {
	private static final long serialVersionUID = 1L;
	private SysTenantDTO sysTenant;
	private RoleVO roleVO;


}
