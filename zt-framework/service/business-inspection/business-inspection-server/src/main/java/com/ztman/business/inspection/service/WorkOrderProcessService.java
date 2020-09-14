package com.ztman.business.inspection.service;

import com.baomidou.mybatisplus.service.IService;
import com.ztman.business.inspection.client.entity.WorkOrderProcess;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.common.core.util.Query;
/**
 * 工单过程表 服务接口层
 *
 * @author zxx
 * @date 2020-08-29 10:34:49
 */
public interface WorkOrderProcessService extends IService<WorkOrderProcess> {
    /**
     * 查询工单过程表信息
     *
     * @param id 工单过程表ID
     * @return 工单过程表信息
     */
    public WorkOrderProcess selectWorkOrderProcessById(Long id);

    /**
     * 查询工单过程表列表
     *
     * @param workOrderProcess 工单过程表信息
     * @return 工单过程表集合
     */
    public List<WorkOrderProcess> selectWorkOrderProcessList(WorkOrderProcess workOrderProcess);


    /**
     * 分页模糊查询工单过程表列表
     * @return 工单过程表集合
     */
    public Page selectWorkOrderProcessListByLike(Query query);



    /**
     * 新增工单过程表
     *
     * @param workOrderProcess 工单过程表信息
     * @return 结果
     */
    public int insertWorkOrderProcess(WorkOrderProcess workOrderProcess);

    /**
     * 修改工单过程表
     *
     * @param workOrderProcess 工单过程表信息
     * @return 结果
     */
    public int updateWorkOrderProcess(WorkOrderProcess workOrderProcess);

    /**
     * 删除工单过程表
     *
     * @param id 工单过程表ID
     * @return 结果
     */
    public int deleteWorkOrderProcessById(Long id);

    /**
     * 批量删除工单过程表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWorkOrderProcessByIds(String[] ids);

}

