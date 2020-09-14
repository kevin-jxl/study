package com.ztman.admin.api.vo;

import com.ztman.admin.api.entity.SysAdministrativeOrgan;
import lombok.Data;

import java.io.Serializable;

/**
 * 行政机构表
 *
 * @author jxl
 * @date 2020-08-21 15:37:10
 */
@Data
//@EqualsAndHashCode(callSuper = true)
public class AdministrativeOrganVO extends SysAdministrativeOrgan implements Serializable {
	/**
	 * 创建人
	 */
	private String createNmae;
}
