package com.ztman.admin.api.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 字典明细视图VO
 */
@Data
public class SysDictVO implements Serializable {
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
     * 有效判断
     */
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
     * IndexId
     */
    private Integer dictIndexId;
}
