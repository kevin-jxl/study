package com.ztman.ztspark.entity.workorder.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工单列表实体类
 * 
 * @author zxx
 * @date 2020-09-03 19:07:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderListVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 记录列表
     */
    private List<WorkOrderVO> records;
    /**
     * 总数
     */
    private int total;

    /**
     * 工单实体类
     * 
     * @author zxx
     * @date 2020-09-03 19:07:55
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WorkOrderVO implements Serializable {
        private static final long serialVersionUID = 1L;
        /**
         * 工单号
         */
        private String id;
        /**
         * 问题等级
         */
        private String problemLevel;
        /**
         * 问题类型
         */
        private String problemType;
        /**
         * 问题描述
         */
        private String problemDesc;
        /**
         * 设备名称
         */
        private String equipmentName;
        /**
         * 设备编号
         */
        private String equipmentId;
        /**
         * 上报人姓名
         */
        private String reportPersonName;
        /**
         * 联系方式
         */
        private String contact;
        /**
         * 进度状态
         */
        private String process;
        /**
         * 上报地点
         */
        private String reportAddr;
        /**
         * 上报时间
         */
        @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime reportTime;
        /**
         * 所属车场
         */
        private String ofPark;
    }

}
