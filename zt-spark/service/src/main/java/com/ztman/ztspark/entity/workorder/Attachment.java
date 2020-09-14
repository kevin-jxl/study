package com.ztman.ztspark.entity.workorder;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 附件表
 *
 * @author zxx
 * @date 2020-09-07 14:46:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("attachment")
public class Attachment extends Model<Attachment> {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 组编码
     */
    private String groupId;
    /**
     * 租户
     */
    private String tenantId;
    /**
     * 上传用户id
     */
    private String upUserId;
    /**
     * 物理相对路径
     */
    private String path;
    /**
     * 文件后缀
     */
    private String suffix;
    /**
     * 文件类型(1.语音 2.图片 3.视频 4.文件)
     */
    private String type;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 备注
     */
    private String note;

    private String fileName;

    /**
     * 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
