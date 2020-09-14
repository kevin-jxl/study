package com.ztman.ztspark.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.common.core.util.Query;
import com.ztman.ztspark.dto.CompanyDutyDTO;
import com.ztman.ztspark.entity.company.CompanyDuty;
import com.ztman.ztspark.vo.CompanyDutyVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 值班信息管理表  mapper层
 *
 * @author spark code generator
 * @date 2020-09-03 16:46:58
 */
@Mapper
public interface CompanyDutyMapper extends BaseMapper<CompanyDuty> {
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
     * 模糊查询值班信息管理表列表
     *
     * @param companyDuty 值班信息管理表信息
     * @return 值班信息管理表集合
     */
    public List<CompanyDuty> selectCompanyDutyListByLike(CompanyDuty companyDuty);


    /**
     * 分页模糊查询值班信息管理表列表
     *
     * @param companyDuty 值班信息管理表信息
     * @return 值班信息管理表集合
     */
    public List<CompanyDutyDTO> selectCompanyDutyListByLike(Query query, CompanyDutyVO companyDutyVO);


    /**
     * 新增值班信息管理表
     *
     * @param companyDuty 值班信息管理表信息
     * @return 结果
     */
    public int insertCompanyDuty(CompanyDuty companyDuty);

    /**
     * 修改值班信息管理表
     *
     * @param companyDuty 值班信息管理表信息
     * @return 结果
     */
    public int updateCompanyDuty(CompanyDuty companyDuty);

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


    List getTenantConpanyId(@Param("tenantId") String tenantId);
}
