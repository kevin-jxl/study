package com.ztman.ztspark.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.common.core.util.Query;
import com.ztman.ztspark.entity.workorder.WorkOrderVerify;

/**
 * 工单核查表  mapper层
 *
 * @author zxx
 * @date 2020-09-07 14:37:25
 */
public interface WorkOrderVerifyMapper extends BaseMapper<WorkOrderVerify> {
    /**
     * 查询工单核查表信息
     *
     * @param id 工单核查表ID
     * @return 工单核查表信息
     */
    public WorkOrderVerify selectWorkOrderVerifyById(Long id);

    /**
     * 查询工单核查表列表
     *
     * @param workOrderVerify 工单核查表信息
     * @return 工单核查表集合
     */
    public List<WorkOrderVerify> selectWorkOrderVerifyList(WorkOrderVerify workOrderVerify);

    /**
     * 模糊查询工单核查表列表
     *
     * @param workOrderVerify 工单核查表信息
     * @return 工单核查表集合
     */
    public List<WorkOrderVerify> selectWorkOrderVerifyListByLike(WorkOrderVerify workOrderVerify);


    /**
     * 分页模糊查询工单核查表列表
     *
     * @param workOrderVerify 工单核查表信息
     * @return 工单核查表集合
     */
    public List<WorkOrderVerify> selectWorkOrderVerifyListByLike(Query query, WorkOrderVerify workOrderVerify);


    /**
     * 新增工单核查表
     *
     * @param workOrderVerify 工单核查表信息
     * @return 结果
     */
    public int insertWorkOrderVerify(WorkOrderVerify workOrderVerify);

    /**
     * 修改工单核查表
     *
     * @param workOrderVerify 工单核查表信息
     * @return 结果
     */
    public int updateWorkOrderVerify(WorkOrderVerify workOrderVerify);

    /**
     * 删除工单核查表
     *
     * @param id 工单核查表ID
     * @return 结果
     */
    public int deleteWorkOrderVerifyById(Long id);

    /**
     * 批量删除工单核查表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteWorkOrderVerifyByIds(String[] ids);



}
