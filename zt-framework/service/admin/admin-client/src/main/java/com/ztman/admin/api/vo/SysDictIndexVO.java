package com.ztman.admin.api.vo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 字典列表
 *
 * @author ztman code generator
 * @date 2019-03-08 10:41:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict_index")
public class SysDictIndexVO extends Model<SysDictIndexVO> {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * 字典标识
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 详细
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
     * 分类ID
     */
    private Integer dictType;
    /**
     * 删除标记 1有效 0无效
     */
    @TableLogic
    private String delFlag;
    /**
     * 创建人
     */
    private String createName;
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
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
