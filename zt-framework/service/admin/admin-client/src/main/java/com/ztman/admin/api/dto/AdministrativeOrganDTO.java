package com.ztman.admin.api.dto;

import com.ztman.admin.api.entity.SysAdministrativeOrgan;
import lombok.Data;

/**
 * 行政机构表
 *
 * @author jxl
 * @date 2020-08-21 15:37:10
 */
@Data
//@EqualsAndHashCode(callSuper = true)
public class AdministrativeOrganDTO extends SysAdministrativeOrgan {
	/**
	 * 创建人
	 */
	private String createNmae;
}
