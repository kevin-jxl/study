package com.ztman.ztspark.service;

import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.common.core.util.Query;
import com.ztman.ztspark.entity.company.PointDutyJob;

import java.util.List;

/**
 * 值班 点检任务 服务接口层
 *
 * @author spark code generator
 * @date 2020-09-08 19:07:37
 */
public interface PointDutyJobService extends IService<PointDutyJob> {
    /**
     * 查询值班 点检任务信息
     *
     * @param id 值班 点检任务ID
     * @return 值班 点检任务信息
     */
    public PointDutyJob selectPointDutyJobById(String id);

    /**
     * 查询值班 点检任务列表
     *
     * @param pointDutyJob 值班 点检任务信息
     * @return 值班 点检任务集合
     */
    public List<PointDutyJob> selectPointDutyJobList(PointDutyJob pointDutyJob);


    /**
     * 分页模糊查询值班 点检任务列表
     * @return 值班 点检任务集合
     */
    public Page selectPointDutyJobListByLike(Query query);



    /**
     * 新增值班 点检任务
     *
     * @param pointDutyJob 值班 点检任务信息
     * @return 结果
     */
    public int insertPointDutyJob(PointDutyJob pointDutyJob);

    /**
     * 修改值班 点检任务
     *
     * @param pointDutyJob 值班 点检任务信息
     * @return 结果
     */
    public int updatePointDutyJob(PointDutyJob pointDutyJob);

    /**
     * 删除值班 点检任务
     *
     * @param id 值班 点检任务ID
     * @return 结果
     */
    public int deletePointDutyJobById(String id);

    /**
     * 批量删除值班 点检任务
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePointDutyJobByIds(String[] ids);

}

