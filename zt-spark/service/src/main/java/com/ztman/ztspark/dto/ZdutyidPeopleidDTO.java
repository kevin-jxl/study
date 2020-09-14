package com.ztman.ztspark.dto;


import com.ztman.ztspark.entity.company.ZdutyidPeopleid;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 值班人员关联表
 *
 * @author spark code generator
 * @date 2020-09-08 19:20:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ZdutyidPeopleidDTO extends ZdutyidPeopleid {
	private static final long serialVersionUID = 1L;

	/**
	 * 人员名称
	 */
	private String peopleName;
	private String peoplePhone;
	private String[] time;

	private List<PointDutyJobDTO> task;

}
