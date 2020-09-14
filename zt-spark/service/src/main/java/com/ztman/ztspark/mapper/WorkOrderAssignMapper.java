package com.ztman.ztspark.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.common.core.util.Query;
import com.ztman.ztspark.entity.workorder.WorkOrderAssign;

/**
 * 工单派遣信息  mapper层
 *
 * @author zxx
 * @date 2020-09-07 14:38:19
 */
public interface WorkOrderAssignMapper extends BaseMapper<WorkOrderAssign> {
    /**
     * 查询工单派遣信息信息
     *
     * @param id 工单派遣信息ID
     * @return 工单派遣信息信息
     */
    public WorkOrderAssign selectWorkOrderAssignById(Long id);

    /**
     * 查询工单派遣信息列表
     *
     * @param workOrderAssign 工单派遣信息信息
     * @return 工单派遣信息集合
     */
    public List<WorkOrderAssign> selectWorkOrderAssignList(WorkOrderAssign workOrderAssign);

    /**
     * 模糊查询工单派遣信息列表
     *
     * @param workOrderAssign 工单派遣信息信息
     * @return 工单派遣信息集合
     */
    public List<WorkOrderAssign> selectWorkOrderAssignListByLike(WorkOrderAssign workOrderAssign);


    /**
     * 分页模糊查询工单派遣信息列表
     *
     * @param workOrderAssign 工单派遣信息信息
     * @return 工单派遣信息集合
     */
    public List<WorkOrderAssign> selectWorkOrderAssignListByLike(Query query, WorkOrderAssign workOrderAssign);


    /**
     * 新增工单派遣信息
     *
     * @param workOrderAssign 工单派遣信息信息
     * @return 结果
     */
    public int insertWorkOrderAssign(WorkOrderAssign workOrderAssign);

    /**
     * 修改工单派遣信息
     *
     * @param workOrderAssign 工单派遣信息信息
     * @return 结果
     */
    public int updateWorkOrderAssign(WorkOrderAssign workOrderAssign);

    /**
     * 删除工单派遣信息
     *
     * @param id 工单派遣信息ID
     * @return 结果
     */
    public int deleteWorkOrderAssignById(Long id);

    /**
     * 批量删除工单派遣信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWorkOrderAssignByIds(String[] ids);



}
