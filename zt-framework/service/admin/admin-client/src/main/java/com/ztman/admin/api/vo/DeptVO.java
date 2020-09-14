package com.ztman.admin.api.vo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门管理VO
 *
 * @author ztman code generator
 * @date 2019-01-21 13:53:35
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dept")
public class DeptVO extends Model<DeptVO> {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer deptId;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer orderNum;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
    /**
     * 是否删除  -1：已删除  0：正常
     */
    private String delFlag;
    /**
     *
     */
    private Integer parentId;
    /**
     * 组织简称
     */
    private String deptSimplify;
    /**
     * 组织负责人
     */
    private String deptLeader;
    /**
     * 组织类别
     */
    private String deptType;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 修改人
     */
    private String updateBy;

    @TableField(exist = false)
    private String parentName;

    /**
     * 部门的层级列表
     */
    private List<Integer> deptList;

    /**
     * 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.deptId;
    }
}
