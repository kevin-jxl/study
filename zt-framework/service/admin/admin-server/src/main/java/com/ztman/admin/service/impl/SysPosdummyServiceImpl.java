package com.ztman.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.admin.api.entity.SysPosdummy;
import com.ztman.admin.mapper.SysPosdummyMapper;
import com.ztman.admin.service.SysPosdummyService;
import com.ztman.common.core.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("sysPosdummyService")
public class SysPosdummyServiceImpl extends ServiceImpl<SysPosdummyMapper, SysPosdummy> implements SysPosdummyService {

    @Autowired
    private SysPosdummyMapper sysPosdummyMapper;

    /**
     * 查询虚拟岗位表信息
     *
     * @param id 虚拟岗位表ID
     * @return 虚拟岗位表信息
     */
    @Override
    public SysPosdummy selectSysPosdummyById(Integer id)
    {
        return sysPosdummyMapper.selectSysPosdummyById(id);
    }

    /**
     * 查询虚拟岗位表列表
     *
     * @param sysPosdummy 虚拟岗位表信息
     * @return 虚拟岗位表集合
     */
    @Override
    public List<SysPosdummy> selectSysPosdummyList(SysPosdummy sysPosdummy)
    {
        return sysPosdummyMapper.selectSysPosdummyList(sysPosdummy);
    }


    /**
     * 分页模糊查询虚拟岗位表列表
     * @return 虚拟岗位表集合
     */
    @Override
    public Page selectSysPosdummyListByLike(Query query)
    {
        SysPosdummy sysPosdummy =  BeanUtil.mapToBean(query.getCondition(), SysPosdummy.class,false);
        query.setRecords(sysPosdummyMapper.selectSysPosdummyListByLike(query,sysPosdummy));
        return query;
    }

    /**
     * 新增虚拟岗位表
     *
     * @param sysPosdummy 虚拟岗位表信息
     * @return 结果
     */
    @Override
    public int insertSysPosdummy(SysPosdummy sysPosdummy)
    {
        return sysPosdummyMapper.insertSysPosdummy(sysPosdummy);
    }

    /**
     * 修改虚拟岗位表
     *
     * @param sysPosdummy 虚拟岗位表信息
     * @return 结果
     */
    @Override
    public int updateSysPosdummy(SysPosdummy sysPosdummy)
    {
        return sysPosdummyMapper.updateSysPosdummy(sysPosdummy);
    }


    /**
     * 删除虚拟岗位表
     *
     * @param id 虚拟岗位表ID
     * @return 结果
     */
    public int deleteSysPosdummyById(Integer id)
    {
        return sysPosdummyMapper.deleteSysPosdummyById( id);
    };


    /**
     * 批量删除虚拟岗位表对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysPosdummyByIds(String[] ids)
    {
        return sysPosdummyMapper.deleteSysPosdummyByIds( ids);
    }

    @Override
    public void posDummyPowers(Integer posdummyId, String powerIds) {
        sysPosdummyMapper.posDummyPowers(posdummyId,powerIds);
    }

    @Override
    public Map sysPosdummyList(Map<String, Object> params) {
        Map<String, List> map = new HashMap<>();
        List<Integer> checkeds = new ArrayList<>();
        List<SysPosdummy> labels = new ArrayList<>();
        Object xnpostName = params.get("xnpostName");
        labels = sysPosdummyMapper.sysPosdummyList(xnpostName);
        if (labels != null && labels.size() > 0) {
            //判断是不是虚拟岗位页面的列表查询
            Object userId = params.get("userId");
            if (userId != null) {
                //查询出来选中的值
                checkeds = sysPosdummyMapper.selectPosdummyChecked(Integer.valueOf(userId.toString()));
            }
        }
        map.put("xnposts", labels);
        map.put("checkeds", checkeds);
        return map;
    }
}
