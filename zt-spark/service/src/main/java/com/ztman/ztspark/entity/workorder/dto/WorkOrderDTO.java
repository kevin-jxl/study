package com.ztman.ztspark.entity.workorder.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 工单实体类
 * 
 * @author zxx
 * @date 2020-09-03 19:07:55
 */
@Data
public class WorkOrderDTO {
    /**
     * 工单号
     */
    private String id;
    /**
     * 租户
     */
    private String tenantId;
    /**
     * 问题等级
     */
    private String problemLevel;
    /**
     * 问题类型
     */
    private String problemType;
    /**
     * 问题描述
     */
    private String problemDesc;
    /**
     * 设备名称
     */
    private String equipmentName;
    /**
     * 设备编号
     */
    private String equipmentId;
    /**
     * 上报人姓名
     */
    private String reportPersonName;
    /**
     * 上报人用户id
     */
    private String reportPersonId;
    /**
     * 上报地点
     */
    private String reportAddr;
    /**
     * 上报时间
     */
    private LocalDateTime reportTime;
    /**
     * 上报经度
     */
    private BigDecimal reportLng;
    /**
     * 上报纬度
     */
    private BigDecimal reportLat;
    /**
     * 数据来源
     */
    private String dataSource;
    /**
     * 紧急状态（0.正常 1.紧急）
     */
    private String exigencyStatus;
    /**
     * 备注
     */
    private String note;
    /**
     * 附件组id
     */
    private String attachmentGroupId;

}
