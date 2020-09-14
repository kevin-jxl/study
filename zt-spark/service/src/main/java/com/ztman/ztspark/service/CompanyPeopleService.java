package com.ztman.ztspark.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ztman.common.core.util.Query;
import com.ztman.ztspark.entity.company.CompanyPeople;

import java.util.List;

/**
 * 人员管理表 服务接口层
 *
 * @author spark code generator
 * @date 2020-09-03 16:46:54
 */
public interface CompanyPeopleService extends IService<CompanyPeople> {
    /**
     * 查询人员管理表信息
     *
     * @param id 人员管理表ID
     * @return 人员管理表信息
     */
    public CompanyPeople selectCompanyPeopleById(Integer id);

    /**
     * 查询人员管理表列表
     *
     * @param companyPeople 人员管理表信息
     * @return 人员管理表集合
     */
    public List<CompanyPeople> selectCompanyPeopleList(CompanyPeople companyPeople);


    /**
     * 分页模糊查询人员管理表列表
     * @return 人员管理表集合
     */
    public Page selectCompanyPeopleListByLike(Query query);



    /**
     * 新增人员管理表
     *
     * @param companyPeople 人员管理表信息
     * @return 结果
     */
    public int insertCompanyPeople(CompanyPeople companyPeople);

    /**
     * 修改人员管理表
     *
     * @param companyPeople 人员管理表信息
     * @return 结果
     */
    public int updateCompanyPeople(CompanyPeople companyPeople);

    /**
     * 删除人员管理表
     *
     * @param id 人员管理表ID
     * @return 结果
     */
    public int deleteCompanyPeopleById(Integer id);

    /**
     * 批量删除人员管理表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCompanyPeopleByIds(String[] ids);

    Page selectCompanyPeopleListByComId(Query objectQuery);

    String selectCompanyIdsByUserId(int userId);

    List<CompanyPeople> inputList(Query query);

    CompanyPeople selectCompanyPeopleByTel(CompanyPeople companyPeople);
}

