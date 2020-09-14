package com.ztman.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.admin.api.entity.SysDict;
import com.ztman.admin.mapper.ExSysDictMapper;
import com.ztman.admin.mapper.SysDictMapper;
import com.ztman.admin.service.SysDictService;
import com.ztman.common.core.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("sysDictService")
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;
    @Autowired
    private ExSysDictMapper exSysDictMapper;

    /**
     * 查询信息
     *
     * @param id ID
     * @return 信息
     */
    @Override
    public SysDict selectSysDictById(Integer id) {
        return sysDictMapper.selectSysDictById(id);
    }

    /**
     * 查询列表
     *
     * @param sysDict 信息
     * @return 集合
     */
    @Override
    public List<SysDict> selectSysDictList(SysDict sysDict) {
        return sysDictMapper.selectSysDictList(sysDict);
    }


    /**
     * 分页模糊查询列表
     *
     * @return 集合
     */
    @Override
    public Page selectSysDictListByLike(Query query) {
        SysDict sysDict = BeanUtil.mapToBean(query.getCondition(), SysDict.class, false);
        query.setRecords(sysDictMapper.selectSysDictListByLike(query, sysDict));
        return query;
    }

    /**
     * 新增
     *
     * @param sysDict 信息
     * @return 结果
     */
    @Override
    public int insertSysDict(SysDict sysDict) {
        return sysDictMapper.insertSysDict(sysDict);
    }

    /**
     * 修改
     *
     * @param sysDict 信息
     * @return 结果
     */
    @Override
    public int updateSysDict(SysDict sysDict) {
        return sysDictMapper.updateSysDict(sysDict);
    }


    /**
     * 删除
     *
     * @param id ID
     * @return 结果
     */
    public int deleteSysDictById(Integer id) {
        return sysDictMapper.deleteSysDictById(id);
    }

    ;


    /**
     * 批量删除对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysDictByIds(String[] ids) {
        return sysDictMapper.deleteSysDictByIds(ids);
    }

    /**
     * 根据字典查询字典明细
     *
     * @param dictId
     * @return
     */
    @Override
    public List selectDicts(Integer dictId) {
        return exSysDictMapper.selectDicts(dictId);
    }

    @Override
    public void insetDictRelevance(Integer dictIndexId, Integer id) {
        exSysDictMapper.insetDictRelevance(dictIndexId,id);
    }
}
