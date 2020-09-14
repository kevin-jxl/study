package com.ztman.ztspark.entity.workorder;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工单核查表
 *
 * @author zxx
 * @date 2020-09-07 14:37:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("work_order_verify")
public class WorkOrderVerify extends Model<WorkOrderVerify> {
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
     * 是否按期完成
     */
    private String isAchieve;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 核查用户
     */
    private String userId;

    /**
     * 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
