package com.ztman.tools.file;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class AttackmentDto {
    /**
     * 序号
     */
    private Integer id;
    /**
     * 文件id
     */
    private String attRelationId;
    /**
     * 文件名
     */
    private String attFileName;
    /**
     * 文件大小
     */
    private BigDecimal attFileSize;
    /**
     * 原始文件名
     */
    private String attViewName;
    /**
     * 文件类型(1:图片,2：文件，3:媒体
     */
    private Integer attFileType;
    /**
     * 上传路径
     */
    private String attPath;
    /**
     * 文件上传时间
     */
    private LocalDateTime attUploadTime;
    /**
     * 文件url
     */
    private String attUrl;
    /**
     * 上传用户id
     */
    private String userId;
    /**
     * 状态码
     */
    private int code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 项目立项id(仅用于业务逻辑，无需存表)
     */
    private Integer invesProjectId;
    /**
     * 创建人姓名
     */
    private String createName;
}

