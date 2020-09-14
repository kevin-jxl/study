package com.ztman.ztspark.entity.workorder;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工单派遣信息
 *
 * @author zxx
 * @date 2020-09-07 14:38:19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("work_order_assign")
public class WorkOrderAssign extends Model<WorkOrderAssign> {
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
