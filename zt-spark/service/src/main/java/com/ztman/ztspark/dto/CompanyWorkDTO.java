package com.ztman.ztspark.dto;

import com.ztman.ztspark.entity.company.CompanyWork;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 考勤管理表
 *
 * @author spark code generator
 * @date 2020-09-03 19:07:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CompanyWorkDTO extends CompanyWork implements Serializable {
    Integer userId;

    Date time;

    String dutyStatus;

    Integer dutyId;

    String dayFlag;

    String workType;
}
