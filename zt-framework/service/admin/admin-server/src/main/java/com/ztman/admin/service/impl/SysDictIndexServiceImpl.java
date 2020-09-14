package com.ztman.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.admin.api.entity.SysDictIndex;
import com.ztman.admin.mapper.ExSysDictIndexMapper;
import com.ztman.admin.mapper.ExSysDictTypeMapper;
import com.ztman.admin.mapper.SysDictIndexMapper;
import com.ztman.admin.service.SysDictIndexService;
import com.ztman.common.core.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("sysDictIndexService")
public class SysDictIndexServiceImpl extends ServiceImpl<SysDictIndexMapper, SysDictIndex> implements SysDictIndexService {

    @Autowired
    private SysDictIndexMapper sysDictIndexMapper;
    @Autowired
    private ExSysDictIndexMapper exSysDictIndexMapper;
    @Autowired
    private ExSysDictTypeMapper exSysDictTypeMapper;


    /**
     * 查询信息
     *
     * @param id ID
     * @return 信息
     */
    @Override
    public SysDictIndex selectSysDictIndexById(Integer id) {
        return sysDictIndexMapper.selectSysDictIndexById(id);
    }

    /**
     * 查询列表
     *
     * @param sysDictIndex 信息
     * @return 集合
     */
    @Override
    public List<SysDictIndex> selectSysDictIndexList(SysDictIndex sysDictIndex) {
        return sysDictIndexMapper.selectSysDictIndexList(sysDictIndex);
    }


    /**
     * 分页模糊查询列表
     *
     * @return 集合
     */
    @Override
    public Page selectSysDictIndexListByLike(Query query) {
        SysDictIndex sysDictIndex = BeanUtil.mapToBean(query.getCondition(), SysDictIndex.class, false);
        query.setRecords(sysDictIndexMapper.selectSysDictIndexListByLike(query, sysDictIndex));
        return query;
    }

    /**
     * 新增
     *
     * @param sysDictIndex 信息
     * @return 结果
     */
    @Override
    public int insertSysDictIndex(SysDictIndex sysDictIndex) {
        return sysDictIndexMapper.insertSysDictIndex(sysDictIndex);
    }

    /**
     * 修改
     *
     * @param sysDictIndex 信息
     * @return 结果
     */
    @Override
    public int updateSysDictIndex(SysDictIndex sysDictIndex) {
        return sysDictIndexMapper.updateSysDictIndex(sysDictIndex);
    }


    /**
     * 删除
     *
     * @param id ID
     * @return 结果
     */
    public int deleteSysDictIndexById(Integer id) {
        return sysDictIndexMapper.deleteSysDictIndexById(id);
    }

    ;


    /**
     * 批量删除对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysDictIndexByIds(String[] ids) {
        return sysDictIndexMapper.deleteSysDictIndexByIds(ids);
    }

    /**
     * 根据分类ID查询字典列表
     *
     * @param typeId
     * @return
     */
    @Override
    public List selectDictIndex(Integer typeId) {
        List list = new ArrayList();
        //先查询出来该节点下的所有子节点
        List<Integer> typeChildren = exSysDictTypeMapper.getChildrenOrgOfDictType(typeId);
        typeChildren.add(0,typeId);
        if (typeChildren != null && typeChildren.size() > 0) {
            //遍历子节点查询列表
            list = exSysDictIndexMapper.selectDictIndex(typeChildren);
        }
        return list;
    }
}
