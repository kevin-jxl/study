package com.ztman.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.admin.api.entity.SysAdministrativeArea;
import com.ztman.admin.mapper.SysAdministrativeAreaMapper;
import com.ztman.admin.service.SysAdministrativeAreaService;
import com.ztman.common.core.util.Query;
import com.ztman.tools.number.RandomCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service("sysAdministrativeAreaService")
public class SysAdministrativeAreaServiceImpl extends ServiceImpl<SysAdministrativeAreaMapper, SysAdministrativeArea> implements SysAdministrativeAreaService {

    @Autowired
    private SysAdministrativeAreaMapper sysAdministrativeAreaMapper;

    /**
     * 查询行政区域表信息
     *
     * @param areaId 行政区域表ID
     * @return 行政区域表信息
     */
    @Override
    public SysAdministrativeArea selectSysAdministrativeAreaById(Integer areaId)
    {
        return sysAdministrativeAreaMapper.selectSysAdministrativeAreaById(areaId);
    }

    /**
     * 查询行政区域表列表
     *
     * @param sysAdministrativeArea 行政区域表信息
     * @return 行政区域表集合
     */
    @Override
    public List<SysAdministrativeArea> selectSysAdministrativeAreaList(SysAdministrativeArea sysAdministrativeArea)
    {
        return sysAdministrativeAreaMapper.selectSysAdministrativeAreaList(sysAdministrativeArea);
    }


    /**
     * 分页模糊查询行政区域表列表
     * @return 行政区域表集合
     */
    @Override
    public Page selectSysAdministrativeAreaListByLike(Query query)
    {
        SysAdministrativeArea sysAdministrativeArea =  BeanUtil.mapToBean(query.getCondition(), SysAdministrativeArea.class,false);
        query.setRecords(sysAdministrativeAreaMapper.selectSysAdministrativeAreaListByLike(query,sysAdministrativeArea));
        return query;
    }

    /**
     * 新增行政区域表
     *
     * @param sysAdministrativeArea 行政区域表信息
     * @return 结果
     */
    @Override
    public int insertSysAdministrativeArea(SysAdministrativeArea sysAdministrativeArea)
    {
        sysAdministrativeArea.setCreateTime(new Date());
        sysAdministrativeArea.setDelFlag("1");

        SysAdministrativeArea s = null;
        do {
            sysAdministrativeArea.setCode(RandomCodeUtils.getCode(6));
            s = sysAdministrativeAreaMapper.selectSysAdministrativeAreaByCode(sysAdministrativeArea.getCode());
        } while (s != null);

        return sysAdministrativeAreaMapper.insertSysAdministrativeArea(sysAdministrativeArea);
    }

    /**
     * 修改行政区域表
     *
     * @param sysAdministrativeArea 行政区域表信息
     * @return 结果
     */
    @Override
    public int updateSysAdministrativeArea(SysAdministrativeArea sysAdministrativeArea)
    {
        return sysAdministrativeAreaMapper.updateSysAdministrativeArea(sysAdministrativeArea);
    }


    /**
     * 删除行政区域表
     *
     * @param areaId 行政区域表ID
     * @return 结果
     */
    public int deleteSysAdministrativeAreaById(Integer areaId)
    {
        return sysAdministrativeAreaMapper.deleteSysAdministrativeAreaById( areaId);
    };


    /**
     * 批量删除行政区域表对象
     *
     * @param areaIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysAdministrativeAreaByIds(String[] areaIds)
    {
        return sysAdministrativeAreaMapper.deleteSysAdministrativeAreaByIds( areaIds);
    }

    @Override
    public List<HashMap> list() {

        return sysAdministrativeAreaMapper.list();
    }

}
