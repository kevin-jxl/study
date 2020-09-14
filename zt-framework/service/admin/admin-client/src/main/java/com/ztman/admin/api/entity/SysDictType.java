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
 * 字典分类
 *
 * @author ztman code generator
 * @date 2019-03-12 14:04:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_type")
public class SysDictType extends Model<SysDictType> {
    private static final long serialVersionUID = 1L;

    /**
     * 序列
     */
    @TableId
    private Integer id;
    /**
     * 名字
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
     * 上级ID
     */
    private Integer parentId;
    /**
     * 删除标记 1有效 0无效
     */
    @TableLogic
    private String delFlag;
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
