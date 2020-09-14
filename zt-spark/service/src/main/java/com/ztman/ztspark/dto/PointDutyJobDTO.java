package com.ztman.ztspark.dto;


import com.ztman.ztspark.entity.company.PointDutyJob;
import lombok.Data;
import java.util.Date;

/**
 * 值班 点检任务
 *
 * @author spark code generator
 * @date 2020-09-08 19:07:37
 */
@Data
public class PointDutyJobDTO extends PointDutyJob {
	private static final long serialVersionUID = 1L;
	private Integer dutyJobId;

	private String[] number;



}
