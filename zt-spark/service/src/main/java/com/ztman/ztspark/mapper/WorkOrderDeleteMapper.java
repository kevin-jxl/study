package com.ztman.ztspark.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.common.core.util.Query;
import com.ztman.ztspark.entity.workorder.WorkOrderDelete;

/**
 * 工单删除表  mapper层
 *
 * @author zxx
 * @date 2020-09-07 14:39:17
 */
public interface WorkOrderDeleteMapper extends BaseMapper<WorkOrderDelete> {
    /**
     * 查询工单删除表信息
     *
     * @param id 工单删除表ID
     * @return 工单删除表信息
     */
    public WorkOrderDelete selectWorkOrderDeleteById(Long id);

    /**
     * 查询工单删除表列表
     *
     * @param workOrderDelete 工单删除表信息
     * @return 工单删除表集合
     */
    public List<WorkOrderDelete> selectWorkOrderDeleteList(WorkOrderDelete workOrderDelete);

    /**
     * 模糊查询工单删除表列表
     *
     * @param workOrderDelete 工单删除表信息
     * @return 工单删除表集合
     */
    public List<WorkOrderDelete> selectWorkOrderDeleteListByLike(WorkOrderDelete workOrderDelete);


    /**
     * 分页模糊查询工单删除表列表
     *
     * @param workOrderDelete 工单删除表信息
     * @return 工单删除表集合
     */
    public List<WorkOrderDelete> selectWorkOrderDeleteListByLike(Query query, WorkOrderDelete workOrderDelete);


    /**
     * 新增工单删除表
     *
     * @param workOrderDelete 工单删除表信息
     * @return 结果
     */
    public int insertWorkOrderDelete(WorkOrderDelete workOrderDelete);

    /**
     * 修改工单删除表
     *
     * @param workOrderDelete 工单删除表信息
     * @return 结果
     */
    public int updateWorkOrderDelete(WorkOrderDelete workOrderDelete);

    /**
     * 删除工单删除表
     *
     * @param id 工单删除表ID
     * @return 结果
     */
    public int deleteWorkOrderDeleteById(Long id);

    /**
     * 批量删除工单删除表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWorkOrderDeleteByIds(String[] ids);



}
