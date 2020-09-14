package com.ztman.ztspark.dto;


import com.ztman.ztspark.entity.company.CompanyDuty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

/**
 * 值班信息管理表
 *
 * @author spark code generator
 * @date 2020-09-03 16:46:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CompanyDutyDTO extends CompanyDuty {
	private static final long serialVersionUID = 1L;
	private List<ZdutyidPeopleidDTO> people;
	private String createName;
	private String createPhone;
	private String organName;
	private String dutyName;
	private String[] time;
	private Boolean taskBool;;
}
