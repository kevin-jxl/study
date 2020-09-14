package com.ztman.ztspark.entity.workorder;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工单督办表
 *
 * @author zxx
 * @date 2020-09-07 14:38:52
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("work_order_supervise")
public class WorkOrderSupervise extends Model<WorkOrderSupervise> {
    private static final long serialVersionUID = 1L;

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
     * 短信提醒手机号
     */
    private String smsReminderPhone;
    /**
     * 督办用户
     */
    private String superviseUserId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 督办事由
     */
    private String reasons;

    /**
     * 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
