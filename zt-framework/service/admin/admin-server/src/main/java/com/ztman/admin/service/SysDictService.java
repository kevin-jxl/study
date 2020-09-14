package com.ztman.admin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ztman.admin.api.entity.SysDict;
import com.ztman.common.core.util.Query;

import java.util.List;

/**
 * 服务接口层
 *
 * @author ztman code generator
 * @date 2019-03-08 13:39:56
 */
public interface SysDictService extends IService<SysDict> {
    /**
     * 查询信息
     *
     * @param id ID
     * @return 信息
     */
    public SysDict selectSysDictById(Integer id);

    /**
     * 查询列表
     *
     * @param sysDict 信息
     * @return 集合
     */
    public List<SysDict> selectSysDictList(SysDict sysDict);


    /**
     * 分页模糊查询列表
     *
     * @return 集合
     */
    public Page selectSysDictListByLike(Query query);


    /**
     * 新增
     *
     * @param sysDict 信息
     * @return 结果
     */
    public int insertSysDict(SysDict sysDict);

    /**
     * 修改
     *
     * @param sysDict 信息
     * @return 结果
     */
    public int updateSysDict(SysDict sysDict);

    /**
     * 删除
     *
     * @param id ID
     * @return 结果
     */
    public int deleteSysDictById(Integer id);

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysDictByIds(String[] ids);

    /**
     * 根据字典查询字典明细
     *
     * @param dictId
     * @return
     */
    List selectDicts(Integer dictId);

    void insetDictRelevance(Integer dictIndexId, Integer id);
}

