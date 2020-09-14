package com.ztman.ztspark.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.ztspark.entity.company.PointDutyJob;
import com.ztman.ztspark.mapper.PointDutyJobMapper;
import com.ztman.ztspark.service.PointDutyJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import cn.hutool.core.bean.BeanUtil;
import com.ztman.common.core.util.Query;

import java.util.List;


@Service("pointDutyJobService")
public class PointDutyJobServiceImpl extends ServiceImpl<PointDutyJobMapper, PointDutyJob> implements PointDutyJobService {

    @Autowired
    private PointDutyJobMapper pointDutyJobMapper;

    /**
     * 查询值班 点检任务信息
     *
     * @param id 值班 点检任务ID
     * @return 值班 点检任务信息
     */
    @Override
    public PointDutyJob selectPointDutyJobById(String id)
    {
        return pointDutyJobMapper.selectPointDutyJobById(id);
    }

    /**
     * 查询值班 点检任务列表
     *
     * @param pointDutyJob 值班 点检任务信息
     * @return 值班 点检任务集合
     */
    @Override
    public List<PointDutyJob> selectPointDutyJobList(PointDutyJob pointDutyJob)
    {
        return pointDutyJobMapper.selectPointDutyJobList(pointDutyJob);
    }


    /**
     * 分页模糊查询值班 点检任务列表
     * @return 值班 点检任务集合
     */
    @Override
    public Page selectPointDutyJobListByLike(Query query)
    {
        PointDutyJob pointDutyJob =  BeanUtil.mapToBean(query.getCondition(), PointDutyJob.class,false);
        query.setRecords(pointDutyJobMapper.selectPointDutyJobListByLike(query,pointDutyJob));
        return query;
    }

    /**
     * 新增值班 点检任务
     *
     * @param pointDutyJob 值班 点检任务信息
     * @return 结果
     */
    @Override
    public int insertPointDutyJob(PointDutyJob pointDutyJob)
    {
        return pointDutyJobMapper.insertPointDutyJob(pointDutyJob);
    }

    /**
     * 修改值班 点检任务
     *
     * @param pointDutyJob 值班 点检任务信息
     * @return 结果
     */
    @Override
    public int updatePointDutyJob(PointDutyJob pointDutyJob)
    {
        return pointDutyJobMapper.updatePointDutyJob(pointDutyJob);
    }


    /**
     * 删除值班 点检任务
     *
     * @param id 值班 点检任务ID
     * @return 结果
     */
    @Override
    public int deletePointDutyJobById(String id)
    {
        return pointDutyJobMapper.deletePointDutyJobById( id);
    };


    /**
     * 批量删除值班 点检任务对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deletePointDutyJobByIds(String[] ids)
    {
        return pointDutyJobMapper.deletePointDutyJobByIds( ids);
    }

}
