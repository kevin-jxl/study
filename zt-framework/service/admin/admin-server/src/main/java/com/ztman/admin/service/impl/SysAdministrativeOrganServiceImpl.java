package com.ztman.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.admin.api.entity.SysAdministrativeOrgan;
import com.ztman.admin.api.vo.AdministrativeOrganVO;
import com.ztman.admin.mapper.SysAdministrativeOrganMapper;
import com.ztman.admin.service.SysAdministrativeOrganService;
import com.ztman.common.core.util.Query;
import com.ztman.tools.number.RandomCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service("sysAdministrativeOrganService")
public class SysAdministrativeOrganServiceImpl extends ServiceImpl<SysAdministrativeOrganMapper, SysAdministrativeOrgan> implements SysAdministrativeOrganService {

    @Autowired
    private SysAdministrativeOrganMapper sysAdministrativeOrganMapper;

    /**
     * 查询行政机构表信息
     *
     * @param organId 行政机构表ID
     * @return 行政机构表信息
     */
    @Override
    public AdministrativeOrganVO selectSysAdministrativeOrganById(Integer organId)
    {
        return sysAdministrativeOrganMapper.selectSysAdministrativeOrganById(organId);
    }

    /**
     * 查询行政机构表列表
     *
     * @param sysAdministrativeOrgan 行政机构表信息
     * @return 行政机构表集合
     */
    @Override
    public List<SysAdministrativeOrgan> selectSysAdministrativeOrganList(SysAdministrativeOrgan sysAdministrativeOrgan)
    {
        return sysAdministrativeOrganMapper.selectSysAdministrativeOrganList(sysAdministrativeOrgan);
    }


    /**
     * 分页模糊查询行政机构表列表
     * @return 行政机构表集合
     */
    @Override
    public Page selectSysAdministrativeOrganListByLike(Query query)
    {
        SysAdministrativeOrgan sysAdministrativeOrgan =  BeanUtil.mapToBean(query.getCondition(), SysAdministrativeOrgan.class,false);
        query.setRecords(sysAdministrativeOrganMapper.selectSysAdministrativeOrganListByLike(query,sysAdministrativeOrgan));
        return query;
    }

    /**
     * 新增行政机构表
     *
     * @param sysAdministrativeOrgan 行政机构表信息
     * @return 结果
     */
    @Override
    public int insertSysAdministrativeOrgan(SysAdministrativeOrgan sysAdministrativeOrgan) {
        sysAdministrativeOrgan.setCreateTime(new Date());
        sysAdministrativeOrgan.setDelFlag("1");

        SysAdministrativeOrgan administrativeOrganByCode = null;
        do {
            sysAdministrativeOrgan.setCode(RandomCodeUtils.getCode(6));
            administrativeOrganByCode = sysAdministrativeOrganMapper.findSysAdministrativeOrganByCode(sysAdministrativeOrgan.getCode());
        } while (administrativeOrganByCode != null);

        return sysAdministrativeOrganMapper.insertSysAdministrativeOrgan(sysAdministrativeOrgan);
    }

    /**
     * 修改行政机构表
     *
     * @param newAo 行政机构表信息
     * @return 结果
     */
    @Override
    public int updateSysAdministrativeOrgan(SysAdministrativeOrgan newAo)
    {
        SysAdministrativeOrgan oldAo = selectById(newAo.getOrganId());
        BeanUtil.copyProperties(newAo, oldAo);

        return sysAdministrativeOrganMapper.updateSysAdministrativeOrgan(oldAo);
    }


    /**
     * 删除行政机构表
     *
     * @param organId 行政机构表ID
     * @return 结果
     */
    public int deleteSysAdministrativeOrganById(Integer organId)
    {
        return sysAdministrativeOrganMapper.deleteSysAdministrativeOrganById( organId);
    };


    /**
     * 批量删除行政机构表对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysAdministrativeOrganByIds(String[] organIds)
    {
        return sysAdministrativeOrganMapper.deleteSysAdministrativeOrganByIds( organIds);
    }

    @Override
    public List<HashMap> list(Query query) {
        SysAdministrativeOrgan sysAdministrativeOrgan =  BeanUtil.mapToBean(query.getCondition(), SysAdministrativeOrgan.class,false);
        return sysAdministrativeOrganMapper.list(sysAdministrativeOrgan);
    }

    @Override
    public String selectCompanyIdsByUserId(Integer userId) {
        List<Integer> ids = sysAdministrativeOrganMapper.selectCompanyIdsByUserId(userId);
        String companyIds = "";
        for (int i = 0; i < ids.size(); i ++) {
            companyIds += "," + ids.get(i);
        }
        if (companyIds.length() > 0) {
            return companyIds.substring(1);
        }
        return null;
    }

    @Override
    public Page selectSysAdministrativeOrganListByLikeAndTenant(Query query) {
        SysAdministrativeOrgan sysAdministrativeOrgan =  BeanUtil.mapToBean(query.getCondition(), SysAdministrativeOrgan.class,false);

        return query.setRecords(sysAdministrativeOrganMapper.selectSysAdministrativeOrganListByLikeAndTenant(query,sysAdministrativeOrgan));
    }

    @Override
    public List<HashMap> inputList(String comId) {
        return sysAdministrativeOrganMapper.inputList(comId);
    }


}
