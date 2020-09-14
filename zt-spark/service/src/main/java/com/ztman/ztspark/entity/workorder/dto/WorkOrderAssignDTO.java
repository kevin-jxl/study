package com.ztman.ztspark.entity.workorder.dto;

import com.baomidou.mybatisplus.annotations.TableId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工单调度实体类
 * 
 * @author zxx
 * @date 2020-09-03 19:07:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderAssignDTO {
    /**
     * 核查编号
     */
    @TableId
    private Long id;
    /**
     * 工单号
     */
    private String workOrderId;
    /**
     * 派遣人用户id
     */
    private String assignUserId;
    /**
     * 员工用户编号
     */
    private String emUserId;
    /**
     * 员工姓名
     */
    private String emName;
    /**
     * 员工编号
     */
    private String emNo;
    /**
     * 联系方式
     */
    private String contact;
    /**
     * 处理时限
     */
    private String pmin;
    /**
     * 任务补充
     */
    private String replenish;
}
