package com.ztman.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.admin.api.entity.SysCompany;
import com.ztman.admin.api.vo.SysCompanyVO;
import com.ztman.admin.mapper.SysCompanyMapper;
import com.ztman.admin.service.SysCompanyService;
import com.ztman.common.core.util.Query;
import com.ztman.tools.number.RandomCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service("sysCompanyService")
public class SysCompanyServiceImpl extends ServiceImpl<SysCompanyMapper, SysCompany> implements SysCompanyService {

    @Autowired
    private SysCompanyMapper sysCompanyMapper;

    /**
     * 查询企业表信息
     *
     * @param comId 企业表ID
     * @return 企业表信息
     */
    @Override
    public SysCompanyVO selectSysCompanyById(Integer comId)
    {
        return sysCompanyMapper.selectSysCompanyById(comId);
    }

    /**
     * 查询企业表列表
     *
     * @param sysCompany 企业表信息
     * @return 企业表集合
     */
    @Override
    public List<SysCompany> selectSysCompanyList(SysCompany sysCompany)
    {
        return sysCompanyMapper.selectSysCompanyList(sysCompany);
    }


    /**
     * 分页模糊查询企业表列表
     * @return 企业表集合
     */
    @Override
    public Page selectSysCompanyListByLike(Query query)
    {
        SysCompany sysCompany =  BeanUtil.mapToBean(query.getCondition(), SysCompany.class,false);
        query.setRecords(sysCompanyMapper.selectSysCompanyListByLike(query,sysCompany));
        return query;
    }

    /**
     * 新增企业表
     *
     * @param sysCompany 企业表信息
     * @return 结果
     */
    @Override
    public int insertSysCompany(SysCompany sysCompany){
        sysCompany.setCreateTime(new Date());
        sysCompany.setDelFlag("1");
        SysCompany company = null;
        do {
            sysCompany.setCode(RandomCodeUtils.getCode(6));
            company = sysCompanyMapper.findSysCompanyByCode(sysCompany.getCode());
        } while (company != null);

        return sysCompanyMapper.insertSysCompany(sysCompany);
    }

    /**
     * 修改企业表
     *
     * @param sysCompany 企业表信息
     * @return 结果
     */
    @Override
    public int updateSysCompany(SysCompany sysCompany)
    {
        return sysCompanyMapper.updateSysCompany(sysCompany);
    }


    /**
     * 删除企业表
     *
     * @param comId 企业表ID
     * @return 结果
     */
    public int deleteSysCompanyById(Integer comId)
    {
        return sysCompanyMapper.deleteSysCompanyById( comId);
    };


    /**
     * 批量删除企业表对象
     *
     * @param comIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysCompanyByIds(String[] comIds)
    {
        return sysCompanyMapper.deleteSysCompanyByIds( comIds);
    }

    @Override
    public int updateStatus(SysCompany sysCompany) {

        return sysCompanyMapper.updateStatus(sysCompany);
    }

    @Override
    public List<HashMap> list(String tenantId) {

        return sysCompanyMapper.list(tenantId);
    }

    @Override
    public List<HashMap> inputList(String tenantId) {
        return sysCompanyMapper.inputList(tenantId);
    }

}
