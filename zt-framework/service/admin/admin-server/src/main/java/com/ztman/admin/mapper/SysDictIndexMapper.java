package com.ztman.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.entity.SysDictIndex;
import com.ztman.admin.api.vo.SysDictIndexVO;
import com.ztman.common.core.util.Query;

import java.util.List;

/**
 *   mapper层
 *
 * @author ztman code generator
 * @date 2019-03-08 10:41:04
 */
public interface SysDictIndexMapper extends BaseMapper<SysDictIndex> {
    /**
     * 查询信息
     *
     * @param id ID
     * @return 信息
     */
    public SysDictIndex selectSysDictIndexById(Integer id);

    /**
     * 查询列表
     *
     * @param sysDictIndex 信息
     * @return 集合
     */
    public List<SysDictIndex> selectSysDictIndexList(SysDictIndex sysDictIndex);

    /**
     * 模糊查询列表
     *
     * @param sysDictIndex 信息
     * @return 集合
     */
    public List<SysDictIndexVO> selectSysDictIndexListByLike(SysDictIndex sysDictIndex);


    /**
     * 分页模糊查询列表
     *
     * @param sysDictIndex 信息
     * @return 集合
     */
    public List<SysDictIndexVO> selectSysDictIndexListByLike(Query query, SysDictIndex sysDictIndex);


    /**
     * 新增
     *
     * @param sysDictIndex 信息
     * @return 结果
     */
    public int insertSysDictIndex(SysDictIndex sysDictIndex);

    /**
     * 修改
     *
     * @param sysDictIndex 信息
     * @return 结果
     */
    public int updateSysDictIndex(SysDictIndex sysDictIndex);

    /**
     * 删除
     *
     * @param id ID
     * @return 结果
     */
    public int deleteSysDictIndexById(Integer id);

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysDictIndexByIds(String[] ids);

}
