package com.ztman.admin.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ztman.admin.api.dto.DeptTree;
import com.ztman.admin.api.entity.SysDept;
import com.ztman.common.core.util.Query;

import java.util.List;

/**
 * <p>
 * 部门管理 服务类
 * </p>
 *
 * @author ztman
 * @since 2018-01-20
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 查询部门树菜单
     *
     * @param sysDeptEntityWrapper
     * @return 树
     */
    List<DeptTree> selectListTree(EntityWrapper<SysDept> sysDeptEntityWrapper);

    /**
     * 添加信息部门
     *
     * @param sysDept
     * @return
     */
    Boolean insertDept(SysDept sysDept);

    /**
     * 删除部门
     *
     * @param id 部门 ID
     * @return 成功、失败
     */
    Boolean deleteDeptById(Integer id);

    /**
     * 更新部门
     *
     * @param sysDept 部门信息
     * @return 成功、失败
     */
    Boolean updateDeptById(SysDept sysDept);

    /**
     * 　　* @Description: 根据部门编码查询列表
     * 　　* @param
     * 　　* @return
     * 　　* @author lmh
     * 　　* @date 2019/1/22 11:20
     *
     */
    Page selectWithDeptPage(Query query);

    List<Integer> getParentOrgOfDept(Integer deptId);
}
