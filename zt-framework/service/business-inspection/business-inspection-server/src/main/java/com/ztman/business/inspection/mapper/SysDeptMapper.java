package com.ztman.business.inspection.mapper;

import com.ztman.business.inspection.client.entity.SysDept;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.common.core.util.Query;

/**
 * 部门  mapper层
 *
 * @author zxx
 * @date 2020-08-30 01:58:13
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {
    /**
     * 查询部门信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    public SysDept selectSysDeptById(Integer deptId);

    /**
     * 查询部门列表
     *
     * @param sysDept 部门信息
     * @return 部门集合
     */
    public List<SysDept> selectSysDeptList(SysDept sysDept);

    /**
     * 模糊查询部门列表
     *
     * @param sysDept 部门信息
     * @return 部门集合
     */
    public List<SysDept> selectSysDeptListByLike(SysDept sysDept);


    /**
     * 分页模糊查询部门列表
     *
     * @param sysDept 部门信息
     * @return 部门集合
     */
    public List<SysDept> selectSysDeptListByLike(Query query, SysDept sysDept);


    /**
     * 新增部门
     *
     * @param sysDept 部门信息
     * @return 结果
     */
    public int insertSysDept(SysDept sysDept);

    /**
     * 修改部门
     *
     * @param sysDept 部门信息
     * @return 结果
     */
    public int updateSysDept(SysDept sysDept);

    /**
     * 删除部门
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteSysDeptById(Integer deptId);

    /**
     * 批量删除部门
     *
     * @param deptIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysDeptByIds(String[] deptIds);



}
