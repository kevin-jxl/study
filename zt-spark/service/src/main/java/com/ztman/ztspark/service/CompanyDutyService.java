package com.ztman.ztspark.service;

import cn.hutool.system.UserInfo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ztman.common.core.util.Query;
import com.ztman.common.security.DTO.SecruityUser;
import com.ztman.ztspark.dto.CompanyDutyDTO;
import com.ztman.ztspark.entity.company.CompanyDuty;

import java.util.List;

/**
 * 值班信息管理表 服务接口层
 *
 * @author spark code generator
 * @date 2020-09-03 16:46:58
 */
public interface CompanyDutyService extends IService<CompanyDuty> {
    /**
     * 查询值班信息管理表信息
     *
     * @param id 值班信息管理表ID
     * @return 值班信息管理表信息
     */
    public CompanyDutyDTO selectCompanyDutyById(Integer id);

    /**
     * 查询值班信息管理表列表
     *
     * @param companyDuty 值班信息管理表信息
     * @return 值班信息管理表集合
     */
    public List<CompanyDuty> selectCompanyDutyList(CompanyDuty companyDuty);


    /**
     * 分页模糊查询值班信息管理表列表
     * @return 值班信息管理表集合
     */
    public Page selectCompanyDutyListByLike(Query query);



    /**
     * 新增值班信息管理表
     *
     * @param companyDuty 值班信息管理表信息
     * @return 结果
     */
    public int insertCompanyDuty(CompanyDutyDTO companyDuty, SecruityUser user);

    /**
     * 修改值班信息管理表
     *
     * @param companyDuty 值班信息管理表信息
     * @return 结果
     */
    public int updateCompanyDuty(CompanyDutyDTO companyDuty, SecruityUser user);

    /**
     * 删除值班信息管理表
     *
     * @param id 值班信息管理表ID
     * @return 结果
     */
    public int deleteCompanyDutyById(Integer id);

    /**
     * 批量删除值班信息管理表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCompanyDutyByIds(String[] ids);

    Page selectDutyPeopleListByLike(Query<Object> objectQuery);
}

