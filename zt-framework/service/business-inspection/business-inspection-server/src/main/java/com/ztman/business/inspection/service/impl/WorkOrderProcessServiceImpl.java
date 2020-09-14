package com.ztman.business.inspection.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import com.ztman.common.core.util.Query;
import com.ztman.business.inspection.mapper.WorkOrderProcessMapper;
import com.ztman.business.inspection.client.entity.WorkOrderProcess;
import com.ztman.business.inspection.service.WorkOrderProcessService;


@Service("workOrderProcessService")
public class WorkOrderProcessServiceImpl extends ServiceImpl<WorkOrderProcessMapper, WorkOrderProcess> implements WorkOrderProcessService {

    @Autowired
    private WorkOrderProcessMapper workOrderProcessMapper;

    /**
     * 查询工单过程表信息
     *
     * @param id 工单过程表ID
     * @return 工单过程表信息
     */
    @Override
    public WorkOrderProcess selectWorkOrderProcessById(Long id)
    {
        return workOrderProcessMapper.selectWorkOrderProcessById(id);
    }

    /**
     * 查询工单过程表列表
     *
     * @param workOrderProcess 工单过程表信息
     * @return 工单过程表集合
     */
    @Override
    public List<WorkOrderProcess> selectWorkOrderProcessList(WorkOrderProcess workOrderProcess)
    {
        return workOrderProcessMapper.selectWorkOrderProcessList(workOrderProcess);
    }


    /**
     * 分页模糊查询工单过程表列表
     * @return 工单过程表集合
     */
    @Override
    public Page selectWorkOrderProcessListByLike(Query query)
    {
        WorkOrderProcess workOrderProcess =  BeanUtil.mapToBean(query.getCondition(), WorkOrderProcess.class,false);
        query.setRecords(workOrderProcessMapper.selectWorkOrderProcessListByLike(query,workOrderProcess));
        return query;
    }

    /**
     * 新增工单过程表
     *
     * @param workOrderProcess 工单过程表信息
     * @return 结果
     */
    @Override
    public int insertWorkOrderProcess(WorkOrderProcess workOrderProcess)
    {
        return workOrderProcessMapper.insertWorkOrderProcess(workOrderProcess);
    }

    /**
     * 修改工单过程表
     *
     * @param workOrderProcess 工单过程表信息
     * @return 结果
     */
    @Override
    public int updateWorkOrderProcess(WorkOrderProcess workOrderProcess)
    {
        return workOrderProcessMapper.updateWorkOrderProcess(workOrderProcess);
    }


    /**
     * 删除工单过程表
     *
     * @param id 工单过程表ID
     * @return 结果
     */
    public int deleteWorkOrderProcessById(Long id)
    {
        return workOrderProcessMapper.deleteWorkOrderProcessById( id);
    };


    /**
     * 批量删除工单过程表对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteWorkOrderProcessByIds(String[] ids)
    {
        return workOrderProcessMapper.deleteWorkOrderProcessByIds( ids);
    }

}
