package com.ztman.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.entity.SysDictType;
import com.ztman.common.core.util.Query;

import java.util.List;

/**
 *   mapper层
 *
 * @author  code generator
 * @date 2019-03-08 09:40:04
 */
public interface SysDictTypeMapper extends BaseMapper<SysDictType> {
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
     * 模糊查询列表
     *
     * @param SysDictType 信息
     * @return 集合
     */
    public List<SysDictType> selectSysDictTypeListByLike(SysDictType SysDictType);


    /**
     * 分页模糊查询列表
     *
     * @param SysDictType 信息
     * @return 集合
     */
    public List<SysDictType> selectSysDictTypeListByLike(Query query, SysDictType SysDictType);


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



}
