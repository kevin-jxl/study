package com.ztman.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.entity.SysCompany;
import com.ztman.admin.api.vo.SysCompanyVO;
import com.ztman.common.core.util.Query;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * 企业表  mapper层
 *
 * @author jxl
 * @date 2020-08-21 10:04:46
 */
public interface SysCompanyMapper extends BaseMapper<SysCompany> {
    /**
     * 查询企业表信息
     *
     * @param comId 企业表ID
     * @return 企业表信息
     */
    public SysCompanyVO selectSysCompanyById(Integer comId);

    /**
     * 查询企业表列表
     *
     * @param sysCompany 企业表信息
     * @return 企业表集合
     */
    public List<SysCompany> selectSysCompanyList(SysCompany sysCompany);

    /**
     * 模糊查询企业表列表
     *
     * @param sysCompany 企业表信息
     * @return 企业表集合
     */
    public List<SysCompanyVO> selectSysCompanyListByLike(SysCompany sysCompany);


    /**
     * 分页模糊查询企业表列表
     *
     * @param sysCompany 企业表信息
     * @return 企业表集合
     */
    public List<SysCompanyVO> selectSysCompanyListByLike(Query query, SysCompany sysCompany);


    /**
     * 新增企业表
     *
     * @param sysCompany 企业表信息
     * @return 结果
     */
    public int insertSysCompany(SysCompany sysCompany);

    /**
     * 修改企业表
     *
     * @param sysCompany 企业表信息
     * @return 结果
     */
    public int updateSysCompany(SysCompany sysCompany);

    /**
     * 删除企业表
     *
     * @param comId 企业表ID
     * @return 结果
     */
    public int deleteSysCompanyById(Integer comId);

    /**
     * 批量删除企业表
     *
     * @param comIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysCompanyByIds(String[] comIds);


    public int updateStatus(SysCompany sysCompany);

    List<HashMap> list(@Param("tenantId") String tenantId);

    SysCompany findSysCompanyByCode(@Param("code") String code);

    List<HashMap> inputList(@Param("tenantId") String tenantId);
}
