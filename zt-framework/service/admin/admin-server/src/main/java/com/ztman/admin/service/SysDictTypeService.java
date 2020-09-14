package com.ztman.admin.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ztman.admin.api.dto.DictTree;
import com.ztman.admin.api.entity.SysDictType;
import com.ztman.common.core.util.Query;

import java.util.List;

/**
 * 服务接口层
 *
 * @author ztman code generator
 * @date 2019-03-08 09:40:04
 */
public interface SysDictTypeService extends IService<SysDictType> {
    /**
     * 查询信息
     *
     * @param id ID
     * @return 信息
     */
    public SysDictType selectSysDictTypeById(Integer id);

    /**
     * 查询列表
     *
     * @param SysDictType 信息
     * @return 集合
     */
    public List<SysDictType> selectSysDictTypeList(SysDictType SysDictType);


    /**
     * 分页模糊查询列表
     *
     * @return 集合
     */
    public Page selectSysDictTypeListByLike(Query query);


    /**
     * 新增
     *
     * @param SysDictType 信息
     * @return 结果
     */
    public int insertSysDictType(SysDictType SysDictType);

    /**
     * 修改
     *
     * @param SysDictType 信息
     * @return 结果
     */
    public int updateSysDictType(SysDictType SysDictType);

    /**
     * 删除
     *
     * @param id ID
     * @return 结果
     */
    public int deleteSysDictTypeById(Integer id);

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysDictTypeByIds(String[] ids);

    /**
     * 获取树形字典菜单
     *
     * @param SysDictTypeEntityWrapper
     * @return
     */
    List<DictTree> selectListTree(EntityWrapper<SysDictType> SysDictTypeEntityWrapper);
}

