package com.ztman.ztspark.entity.workorder;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工单维修信息
 *
 * @author zxx
 * @date 2020-09-07 14:45:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("maintenance")
public class Maintenance extends Model<Maintenance> {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 工单号
     */
    private String workOrderId;
    /**
     * 描述
     */
    private String description;
    /**
     * 维修完成时间
     */
    private LocalDateTime completeDatetime;
    /**
     * 附件组ID
     */
    private String attachmentGroupId;
    /**
     * 维修人id
     */
    private String doUserId;
    /**
     * 维修人姓名
     */
    private String doUserName;
    /**
     * 备件
     */
    private String part;

    /**
     * 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
