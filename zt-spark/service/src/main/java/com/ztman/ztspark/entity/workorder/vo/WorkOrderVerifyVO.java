package com.ztman.ztspark.entity.workorder.vo;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工单核查实体类
 * 
 * @author zxx
 * @date 2020-09-03 19:07:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderVerifyVO {
    /**
     * 工单号
     */
    private String workOrderId;
    /**
     * 完成情况说明
     */
    private String description;
    /**
     * 是否按期完成
     */
    private String isAchieve;
    /**
     * 核查人
     */
    private String personal;
    /**
     * 核查时间
     */
    private LocalDateTime operationTime;
    /**
     * 维修附件
     */
    private List<MaintenanceAttachment> attachments;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class MaintenanceAttachment {
        private Long id;
        /**
         * 虚拟路径
         */
        private String path;
        /**
         * 文件类型(1.语音 2.图片 3.视频 4.文件)
         */
        private String type;
    }
}
