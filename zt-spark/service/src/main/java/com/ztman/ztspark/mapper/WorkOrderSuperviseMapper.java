package com.ztman.ztspark.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.ztspark.entity.workorder.WorkOrderSupervise;

/**
 * 工单督办表  mapper层
 *
 * @author zxx
 * @date 2020-09-07 14:38:52
 */
public interface WorkOrderSuperviseMapper extends BaseMapper<WorkOrderSupervise> {
    
    /**
     * 新增工单督办表
     *
     * @param workOrderSupervise 工单督办表信息
     * @return 结果
     */
    public int insertWorkOrderSupervise(WorkOrderSupervise workOrderSupervise);

    
    public String getSmsReminderPhoneByWorkOrderId(@Param("workOrderId") String workOrderId);



}
