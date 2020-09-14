package com.ztman.admin.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 字典明细
 *
 * @author ztman code generator
 * @date 2019-03-08 13:39:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict")
public class SysDict extends Model<SysDict> {
    private static final long serialVersionUID = 1L;

    /**
     * 序列
     */
    @TableId
    private Integer id;
    /**
     * 字典对照码
     */
    private String code;
    /**
     * 字典对照值
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 排序
     */
    private BigDecimal sort;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 删除标记 1有效 0无效
     */
    @TableLogic
    private String delFlag;
    /**
     * 是否有效
     */
    private String dictFlag;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
