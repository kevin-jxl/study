package com.ztman.ztspark.entity.workorder.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工单派遣实体类
 * 
 * @author zxx
 * @date 2020-09-03 19:07:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderAssignVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private int total;
    private List<WorkOrderVO> records;

    /**
     * 工单内部实体类
     * 
     * @author zxx
     * @date 2020-09-03 19:07:55
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WorkOrderVO implements Serializable {
        
        private static final long serialVersionUID = 1L;
        private String id;
        private String process;
        private String equipmentName;
        private String problemType;
        private String reportAddr;
        private LocalDateTime reportTime;
        private String reportPersonName;
        private String problemDesc;
        private String reportLng;
        private String reportLat;
        private String attachmentGroupId;
    }
}
