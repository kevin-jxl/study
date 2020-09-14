package com.ztman.business.inspection.mapper;

import com.ztman.business.inspection.client.dto.PoininspectionJobDTO;
import com.ztman.business.inspection.client.entity.PoininspectionJob;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.common.core.util.Query;

/**
 * 点检任务  mapper层
 *
 * @author zxx
 * @date 2020-08-29 10:31:01
 */
public interface PoininspectionJobMapper extends BaseMapper<PoininspectionJob> {
    /**
     * 查询点检任务信息
     *
     * @param id 点检任务ID
     * @return 点检任务信息
     */
    public PoininspectionJob selectPoininspectionJobById(String id);

    /**
     * 查询点检任务列表
     *
     * @param poininspectionJob 点检任务信息
     * @return 点检任务集合
     */
    public List<PoininspectionJob> selectPoininspectionJobList(PoininspectionJob poininspectionJob);

    /**
     * 模糊查询点检任务列表
     *
     * @param poininspectionJob 点检任务信息
     * @return 点检任务集合
     */
    public List<PoininspectionJob> selectPoininspectionJobListByLike(PoininspectionJob poininspectionJob);


    /**
     * 分页模糊查询点检任务列表
     *
     * @param poininspectionJob 点检任务信息
     * @return 点检任务集合
     */
    public List<PoininspectionJob> selectPoininspectionJobListByLike(Query query, PoininspectionJob poininspectionJob);


    /**
     * 新增点检任务
     *
     * @param poininspectionJob 点检任务信息
     * @return 结果
     */
    public int insertPoininspectionJob(PoininspectionJob poininspectionJob);

    /**
     * 修改点检任务
     *
     * @param poininspectionJob 点检任务信息
     * @return 结果
     */
    public int updatePoininspectionJob(PoininspectionJob poininspectionJob);

    /**
     * 删除点检任务
     *
     * @param id 点检任务ID
     * @return 结果
     */
    public int deletePoininspectionJobById(String id);

    /**
     * 批量删除点检任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePoininspectionJobByIds(String[] ids);

	public List<PoininspectionJobDTO> cycleList(int page, int size, int type, Integer userId);



}
