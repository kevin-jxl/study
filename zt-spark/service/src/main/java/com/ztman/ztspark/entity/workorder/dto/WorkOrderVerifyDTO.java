package com.ztman.ztspark.entity.workorder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工单核查实体类
 * 
 * @author zxx
 * @date 2020-09-03 19:07:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkOrderVerifyDTO {
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
     * 是否按期完成
     */
    private String isAchieve;
    /**
     * 核查人用户ID
     */
    private String userId;
}
