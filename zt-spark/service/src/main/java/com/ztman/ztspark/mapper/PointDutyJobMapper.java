package com.ztman.ztspark.mapper;

import com.ztman.ztspark.entity.company.PointDutyJob;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.common.core.util.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 值班 点检任务  mapper层
 *
 * @author spark code generator
 * @date 2020-09-08 19:07:37
 */
public interface PointDutyJobMapper extends BaseMapper<PointDutyJob> {
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
     * @param PointDutyJob 值班 点检任务信息
     * @return 值班 点检任务集合
     */
    public List<PointDutyJob> selectPointDutyJobList(PointDutyJob PointDutyJob);

    /**
     * 模糊查询值班 点检任务列表
     *
     * @param PointDutyJob 值班 点检任务信息
     * @return 值班 点检任务集合
     */
    public List<PointDutyJob> selectPointDutyJobListByLike(PointDutyJob PointDutyJob);


    /**
     * 分页模糊查询值班 点检任务列表
     *
     * @param PointDutyJob 值班 点检任务信息
     * @return 值班 点检任务集合
     */
    public List<PointDutyJob> selectPointDutyJobListByLike(Query query, PointDutyJob PointDutyJob);


    /**
     * 新增值班 点检任务
     *
     * @param PointDutyJob 值班 点检任务信息
     * @return 结果
     */
    public int insertPointDutyJob(PointDutyJob PointDutyJob);

    /**
     * 修改值班 点检任务
     *
     * @param PointDutyJob 值班 点检任务信息
     * @return 结果
     */
    public int updatePointDutyJob(PointDutyJob PointDutyJob);

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


    void deleteDataByPeopleId(@Param("peopleId") Integer peopleId);

    void insertZtDutyId_PeopleIdTask(@Param("companyDutyId") Integer companyDutyId,@Param("pointDutyJobId") Integer id,@Param("dutyPeopleId")  Integer dutyPeopleId);

    void deleteZtDutyIdPeopleIdTaskByCompanyDutyId(@Param("companyDutyId")Integer id);

    void deletePointDutyJobByDutyId(@Param("dutyId")Integer id);
}
