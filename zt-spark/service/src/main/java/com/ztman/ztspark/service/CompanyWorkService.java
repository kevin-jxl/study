package com.ztman.ztspark.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ztman.common.core.util.Query;
import com.ztman.ztspark.entity.company.CompanyPeople;
import com.ztman.ztspark.entity.company.CompanyWork;

import java.util.List;

/**
 * 考勤管理表 服务接口层
 *
 * @author spark code generator
 * @date 2020-09-03 19:07:55
 */
public interface CompanyWorkService extends IService<CompanyWork> {
    /**
     * 查询考勤管理表信息
     *
     * @param workId 考勤管理表ID
     * @return 考勤管理表信息
     */
    public CompanyWork selectCompanyWorkById(Integer workId);

    /**
     * 查询考勤管理表列表
     *
     * @param companyWork 考勤管理表信息
     * @return 考勤管理表集合
     */
    public List<CompanyWork> selectCompanyWorkList(CompanyWork companyWork);


    /**
     * 分页模糊查询考勤管理表列表
     * @return 考勤管理表集合
     */
    public Page selectCompanyWorkListByLike(Query query);



    /**
     * 新增考勤管理表
     *
     * @param companyWork 考勤管理表信息
     * @return 结果
     */
    public int insertCompanyWork(CompanyWork companyWork);

    /**
     * 修改考勤管理表
     *
     * @param companyWork 考勤管理表信息
     * @return 结果
     */
    public int updateCompanyWork(CompanyWork companyWork);

    /**
     * 删除考勤管理表
     *
     * @param workId 考勤管理表ID
     * @return 结果
     */
    public int deleteCompanyWorkById(Integer workId);

    /**
     * 批量删除考勤管理表
     *
     * @param workIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteCompanyWorkByIds(String[] workIds);

    void generateDutyInfo();

    int addCompanyWorkByPeople(CompanyPeople companyPeople);

    int deleteCompanyWorkByPeopleId(Integer id);
}

