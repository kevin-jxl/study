package com.ztman.ztspark.entity.workorder.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工单过程实体类
 * 
 * @author zxx
 * @date 2020-09-03 19:07:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderProcessDTO {
    /**
     * 过程标识（1.已派遣 2.已受理 3.处置中 4.已完成 5.已关闭）
     */
    private String tag;
    /**
     * 过程标识名称（1.已派遣 2.已受理 3.处置中 4.已完成 5.已关闭）
     */
    private String tagName;
    /**
     * 该过程参与人姓名
     */
    private String joinName;
    /**
     * 该过程操作时间
     */
    private LocalDateTime operationTime;
}
