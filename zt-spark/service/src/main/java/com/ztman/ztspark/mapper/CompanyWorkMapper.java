package com.ztman.ztspark.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.common.core.util.Query;
import com.ztman.ztspark.entity.company.CompanyPeople;
import com.ztman.ztspark.entity.company.CompanyWork;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 考勤管理表  mapper层
 *
 * @author wsd
 * @date 2020-09-03 16:47:00
 */
public interface CompanyWorkMapper extends BaseMapper<CompanyWork> {
    /**
     * 查询考勤管理表信息
     *
     * @param id 考勤管理表ID
     * @return 考勤管理表信息
     */
    public CompanyWork selectCompanyWorkById(Integer id);

    /**
     * 查询考勤管理表列表
     *
     * @param companyWork 考勤管理表信息
     * @return 考勤管理表集合
     */
    public List<CompanyWork> selectCompanyWorkList(CompanyWork companyWork);

    /**
     * 模糊查询考勤管理表列表
     *
     * @param companyWork 考勤管理表信息
     * @return 考勤管理表集合
     */
    public List<CompanyWork> selectCompanyWorkListByLike(CompanyWork companyWork);


    /**
     * 分页模糊查询考勤管理表列表
     *
     * @param companyWork 考勤管理表信息
     * @return 考勤管理表集合
     */
    List<CompanyWork> selectCompanyWorkListByLike(Query query, CompanyWork companyWork);


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
     * @param id 考勤管理表ID
     * @return 结果
     */
    public int deleteCompanyWorkById(Integer id);

    /**
     * 批量删除考勤管理表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCompanyWorkByIds(String[] ids);

    int generateDutyInfo(@Param("first") String first, @Param("last") String last);

    int addCompanyWorkByPeople(CompanyPeople companyPeople);

    int deleteCompanyWorkByPeopleId(Integer id);
}
