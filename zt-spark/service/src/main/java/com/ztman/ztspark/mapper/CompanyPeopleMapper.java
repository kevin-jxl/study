package com.ztman.ztspark.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.entity.SysUser;
import com.ztman.common.core.util.Query;
import com.ztman.ztspark.entity.company.CompanyPeople;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 人员管理表  mapper层
 *
 * @author spark code generator
 * @date 2020-09-03 16:46:54
 */
public interface CompanyPeopleMapper extends BaseMapper<CompanyPeople> {
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
     * 模糊查询人员管理表列表
     *
     * @param companyPeople 人员管理表信息
     * @return 人员管理表集合
     */
    public List<CompanyPeople> selectCompanyPeopleListByLike(CompanyPeople companyPeople);


    /**
     * 分页模糊查询人员管理表列表
     *
     * @param companyPeople 人员管理表信息
     * @return 人员管理表集合
     */
    public List<CompanyPeople> selectCompanyPeopleListByLike(Query query, CompanyPeople companyPeople);


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


    public int insertSysConsumer(SysUser sysUser);

    List selectCompanyPeopleListByComId(Query query, CompanyPeople companyPeople);

    List<Integer> selectCompanyIdsByUserId(int userId);

    int updateSysUser(SysUser sysUser);

    List<CompanyPeople> inputList(CompanyPeople companyPeople);

    SysUser selectCompanyPeopleByUserName(@Param("name") String username);

    CompanyPeople selectCompanyPeopleByTel(CompanyPeople companyPeople);
}
