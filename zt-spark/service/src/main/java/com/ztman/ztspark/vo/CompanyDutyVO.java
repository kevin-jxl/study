package com.ztman.ztspark.vo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ztman.ztspark.dto.PointDutyJobDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class CompanyDutyVO extends Model<CompanyDutyVO> implements Serializable {
    private Integer id;
    /**
     * 值班人员
     */
    private String dutyName;
    /**
     *值班人联系电话
     */
    private String dutyPhone;
    /**
     *负责车场
     */
    private String organName;
    /**
     *开始时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startTime;
    /**
     *结束时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endTime;
    private String[] time;
    private String date;
    /**
     *值班状态
     */
    private String dutyStatus;
    /**
     *创建人（排班人）
     */
    private String createName;
    /**
     *创建人联系电话
     */
    private String createPhone;
    private String comId;
    private List comIds;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
