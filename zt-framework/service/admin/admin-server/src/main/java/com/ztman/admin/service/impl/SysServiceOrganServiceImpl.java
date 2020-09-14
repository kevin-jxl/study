package com.ztman.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.admin.api.entity.SysServiceOrgan;
import com.ztman.admin.api.vo.SysServiceOrganVO;
import com.ztman.admin.mapper.SysServiceOrganMapper;
import com.ztman.admin.service.SysServiceOrganService;
import com.ztman.common.core.util.Query;
import com.ztman.tools.number.RandomCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service("sysServiceOrganService")
public class SysServiceOrganServiceImpl extends ServiceImpl<SysServiceOrganMapper, SysServiceOrgan> implements SysServiceOrganService {

    @Autowired
    private SysServiceOrganMapper sysServiceOrganMapper;

    /**
     * 查询业务机构表信息
     *
     * @param serviceId 业务机构表ID
     * @return 业务机构表信息
     */
    @Override
    public SysServiceOrganVO selectSysServiceOrganById(Integer serviceId)
    {
        return sysServiceOrganMapper.selectSysServiceOrganById(serviceId);
    }

    /**
     * 查询业务机构表列表
     *
     * @param sysServiceOrgan 业务机构表信息
     * @return 业务机构表集合
     */
    @Override
    public List<SysServiceOrgan> selectSysServiceOrganList(SysServiceOrgan sysServiceOrgan)
    {
        return sysServiceOrganMapper.selectSysServiceOrganList(sysServiceOrgan);
    }


    /**
     * 分页模糊查询业务机构表列表
     * @return 业务机构表集合
     */
    @Override
    public Page selectSysServiceOrganListByLike(Query query)
    {
        SysServiceOrgan sysServiceOrgan =  BeanUtil.mapToBean(query.getCondition(), SysServiceOrgan.class,false);
        query.setRecords(sysServiceOrganMapper.selectSysServiceOrganListByLike(query,sysServiceOrgan));
        return query;
    }

    /**
     * 新增业务机构表
     *
     * @param sysServiceOrgan 业务机构表信息
     * @return 结果
     */
    @Override
    public int insertSysServiceOrgan(SysServiceOrgan sysServiceOrgan)
    {
        sysServiceOrgan.setCreateTime(new Date());
        sysServiceOrgan.setDelFlag("1");
        SysServiceOrgan sysServiceOrganByCode = null;
        do {
            sysServiceOrgan.setCode(RandomCodeUtils.getCode(6));
            sysServiceOrganByCode = sysServiceOrganMapper.findSysServiceOrganByCode(sysServiceOrgan.getCode());
        } while (sysServiceOrganByCode != null);

        return sysServiceOrganMapper.insertSysServiceOrgan(sysServiceOrgan);
    }

    /**
     * 修改业务机构表
     *
     * @param sysServiceOrgan 业务机构表信息
     * @return 结果
     */
    @Override
    public int updateSysServiceOrgan(SysServiceOrgan sysServiceOrgan)
    {
        return sysServiceOrganMapper.updateSysServiceOrgan(sysServiceOrgan);
    }


    /**
     * 删除业务机构表
     *
     * @param serviceId 业务机构表ID
     * @return 结果
     */
    public int deleteSysServiceOrganById(Integer serviceId)
    {
        return sysServiceOrganMapper.deleteSysServiceOrganById( serviceId);
    };


    /**
     * 批量删除业务机构表对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysServiceOrganByIds(String[] serviceIds)
    {
        return sysServiceOrganMapper.deleteSysServiceOrganByIds( serviceIds);
    }

    @Override
    public String selectOrganIdsByUserId(Integer userId) {
        List<Integer> ids = sysServiceOrganMapper.selectOrganIdsByUserId(userId);
        String organIds = "";
        for (int i = 0; i < ids.size(); i ++) {
            organIds += "," + ids.get(i);
        }
        if (organIds.length() > 0) {
            return organIds.substring(1);
        }
        return null;
    }

    @Override
    public Page selectSysServiceOrganListByLikeOrgan(Query query) {
        SysServiceOrgan sysServiceOrgan =  BeanUtil.mapToBean(query.getCondition(), SysServiceOrgan.class,false);
        query.setRecords(sysServiceOrganMapper.selectSysServiceOrganListByLikeOrgan(query,sysServiceOrgan));
        return query;
    }

    @Override
    public List<HashMap<String, Object>> inputList(Query query) {
        SysServiceOrgan sysServiceOrgan =  BeanUtil.mapToBean(query.getCondition(), SysServiceOrgan.class,false);
        return sysServiceOrganMapper.inputList(sysServiceOrgan);
    }

    @Override
    public List<HashMap<String, Object>> list(String organId) {
        return sysServiceOrganMapper.list(organId);
    }

}
