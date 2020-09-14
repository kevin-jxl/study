package com.ztman.admin.api.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 虚拟岗位表
 *
 * @author ztman code generator
 * @date 2019-02-22 14:43:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_post_dummy")
public class SysPosdummy extends Model<SysPosdummy> {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     * 岗位编码
     */
    private String postCode;
    /**
     * 岗位名称
     */
    private String postName;
    /**
     * 排序号
     */
    private Integer postOder;
    /**
     * 岗位类别 暂空
     */
    private String postType;
    /**
     * 岗位状态 0：状态 1：停用
     */
    private String postFlag;
    /**
     * 是否删除 0：正常 1删除
     */
    private Integer delFlag;
    /**
     * 权限标签列表
     */
    private String powerIds;
    /**
     * 创建时间
     */
    private LocalDateTime createtime;
    /**
     * 修改时间
     */
    private LocalDateTime updatetime;
    /**
     *
     */
    private LocalDateTime createBy;
    /**
     *
     */
    private LocalDateTime updateBy;
    /**
     * 备注
     */
    private String remark;
    /**
     * 管理员备注
     */
    private String adminRemark;

    /**
     * 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
