package com.ztman.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.entity.SysDict;
import com.ztman.common.core.util.Query;

import java.util.List;

/**
 * mapper层
 *
 * @author ztman code generator
 * @date 2019-03-08 13:39:56
 */
public interface SysDictMapper extends BaseMapper<SysDict> {
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
     * 模糊查询列表
     *
     * @param sysDict 信息
     * @return 集合
     */
    public List<SysDict> selectSysDictListByLike(SysDict sysDict);


    /**
     * 分页模糊查询列表
     *
     * @param sysDict 信息
     * @return 集合
     */
    public List<SysDict> selectSysDictListByLike(Query query, SysDict sysDict);


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


}
