package com.ztman.ztspark.entity.workorder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 维修工单
 *
 * @author zxx
 * @date 2020-09-07 14:56:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("work_order")
public class WorkOrder extends Model<WorkOrder> {
    private static final long serialVersionUID = 1L;

    /**
     * 工单号
     */
    @TableId
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
     * 上报人
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
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 派遣用户id
     */
    private String assignUserId;
    /**
     * 派遣用户姓名
     */
    private String assignUserName;
    /**
     * 删除标识(0正常 1删除)
     */
    private String delFlag;
    /**
     * 所属车场
     */
    private String ofPark;
    /**
     * 所属车场ID
     */
    private String ofParkId;

    /**
     * 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
