package com.ztman.business.inspection.mapper;

import com.ztman.business.inspection.client.entity.SysCompany;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.common.core.util.Query;

/**
 * 企业  mapper层
 *
 * @author zxx
 * @date 2020-08-30 01:59:00
 */
public interface SysCompanyMapper extends BaseMapper<SysCompany> {
    /**
     * 查询企业信息
     *
     * @param comId 企业ID
     * @return 企业信息
     */
    public SysCompany selectSysCompanyById(Integer comId);

    /**
     * 查询企业列表
     *
     * @param sysCompany 企业信息
     * @return 企业集合
     */
    public List<SysCompany> selectSysCompanyList(SysCompany sysCompany);

    /**
     * 模糊查询企业列表
     *
     * @param sysCompany 企业信息
     * @return 企业集合
     */
    public List<SysCompany> selectSysCompanyListByLike(SysCompany sysCompany);


    /**
     * 分页模糊查询企业列表
     *
     * @param sysCompany 企业信息
     * @return 企业集合
     */
    public List<SysCompany> selectSysCompanyListByLike(Query query, SysCompany sysCompany);


    /**
     * 新增企业
     *
     * @param sysCompany 企业信息
     * @return 结果
     */
    public int insertSysCompany(SysCompany sysCompany);

    /**
     * 修改企业
     *
     * @param sysCompany 企业信息
     * @return 结果
     */
    public int updateSysCompany(SysCompany sysCompany);

    /**
     * 删除企业
     *
     * @param comId 企业ID
     * @return 结果
     */
    public int deleteSysCompanyById(Integer comId);

    /**
     * 批量删除企业
     *
     * @param comIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysCompanyByIds(String[] comIds);



}
