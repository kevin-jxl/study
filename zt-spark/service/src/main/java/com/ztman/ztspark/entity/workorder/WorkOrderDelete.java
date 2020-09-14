package com.ztman.ztspark.entity.workorder;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工单删除表
 *
 * @author zxx
 * @date 2020-09-07 15:08:49
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("work_order_delete")
public class WorkOrderDelete extends Model<WorkOrderDelete> {
    private static final long serialVersionUID = 1L;

    /**
     * 删除编号
     */
    @TableId
    private Long id;
    /**
     * 工单号
     */
    private String workOrderId;
    /**
     * 删除人用户id
     */
    private String delUserId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
