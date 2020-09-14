package com.ztman.ztspark.entity.workorder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工单督办实体类
 * 
 * @author zxx
 * @date 2020-09-03 19:07:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderSuperviseDTO {
    /**
     * 核查编号
     */
    private Long id;
    /**
     * 工单号
     */
    private String workOrderId;
    /**
     * 完成情况说明
     */
    private String description;
    /**
     * 督办时限
     */
    private String pmin;
    /**
     * 短信提醒
     */
    private String smsReminder;
    /**
     * 督办用户
     */
    private String superviseUserId;
    /**
     * 督办事由
     */
    private String reasons;

}
