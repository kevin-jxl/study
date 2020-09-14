package com.ztman.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.admin.api.dto.DictTree;
import com.ztman.admin.api.entity.SysDictType;
import com.ztman.admin.mapper.SysDictTypeMapper;
import com.ztman.admin.api.vo.TreeUtil;
import com.ztman.admin.service.SysDictTypeService;
import com.ztman.common.core.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("SysDictTypeService")
public class SysDictTypeServiceImpl extends ServiceImpl<com.ztman.admin.mapper.SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    @Autowired
    private SysDictTypeMapper SysDictTypeMapper;

    /**
     * 查询信息
     *
     * @param id ID
     * @return 信息
     */
    @Override
    public SysDictType selectSysDictTypeById(Integer id) {
        return SysDictTypeMapper.selectSysDictTypeById(id);
    }

    /**
     * 查询列表
     *
     * @param SysDictType 信息
     * @return 集合
     */
    @Override
    public List<SysDictType> selectSysDictTypeList(SysDictType SysDictType) {
        return SysDictTypeMapper.selectSysDictTypeList(SysDictType);
    }


    /**
     * 分页模糊查询列表
     *
     * @return 集合
     */
    @Override
    public Page selectSysDictTypeListByLike(Query query) {
        SysDictType SysDictType = BeanUtil.mapToBean(query.getCondition(), SysDictType.class, false);
        query.setRecords(SysDictTypeMapper.selectSysDictTypeListByLike(query, SysDictType));
        return query;
    }

    /**
     * 新增
     *
     * @param SysDictType 信息
     * @return 结果
     */
    @Override
    public int insertSysDictType(SysDictType SysDictType) {
        return SysDictTypeMapper.insertSysDictType(SysDictType);
    }

    /**
     * 修改
     *
     * @param SysDictType 信息
     * @return 结果
     */
    @Override
    public int updateSysDictType(SysDictType SysDictType) {
        return SysDictTypeMapper.updateSysDictType(SysDictType);
    }


    /**
     * 删除
     *
     * @param id ID
     * @return 结果
     */
    public int deleteSysDictTypeById(Integer id) {
        return SysDictTypeMapper.deleteSysDictTypeById(id);
    }

    ;


    /**
     * 批量删除对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysDictTypeByIds(String[] ids) {
        return SysDictTypeMapper.deleteSysDictTypeByIds(ids);
    }

    /**
     * 获取树形字典菜单
     *
     * @param SysDictTypeEntityWrapper
     * @return
     */
    @Override
    public List<DictTree> selectListTree(EntityWrapper<SysDictType> SysDictTypeEntityWrapper) {
        SysDictTypeEntityWrapper.orderBy("sort", false);
        List<SysDictType> lists = this.selectList(SysDictTypeEntityWrapper);
        List<DictTree> deptTree = getDictTree(lists, 0);
        return deptTree;
    }

    /**
     * 构建字典树
     *
     * @param dictTypes 字典
     * @param root  根节点
     * @return
     */
    private List<DictTree> getDictTree(List<SysDictType> dictTypes, int root) {
        List<DictTree> trees = new ArrayList<>();
        DictTree node;
        for (SysDictType dicType : dictTypes) {
            if (dicType.getParentId().equals(dicType.getId())) {
                continue;
            }
            node = new DictTree();
            node.setId(dicType.getId());
            node.setParentId(dicType.getParentId());
            node.setName(dicType.getName());
            trees.add(node);
        }
        List<DictTree> bulid = TreeUtil.bulid(trees, root);
        return bulid;
    }
}
