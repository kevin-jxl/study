package com.ztman.admin.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.dto.DeptTree;
import com.ztman.admin.api.entity.SysDept;
import com.ztman.admin.api.vo.DeptVO;
import com.ztman.admin.service.SysDeptService;
import com.ztman.common.core.constant.CommonConstant;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import com.ztman.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门管理 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/dept")
public class DeptController {
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 通过ID查询
     *
     * @param id ID
     * @return SysDept
     */
    @GetMapping("/{id}")
    public DeptVO get(@PathVariable Integer id) {
        List<Integer> deptList = new ArrayList<>();
        DeptVO deptVO = new DeptVO();
        SysDept sysDept = sysDeptService.selectById(id);
        BeanUtils.copyProperties(sysDept, deptVO);
        if (sysDept != null) {
            if (sysDept.getDeptId() != null) {
                deptList = sysDeptService.getParentOrgOfDept(sysDept.getDeptId());
                deptList.remove(deptList.size()-1);
                deptList.add(0,0);
                deptVO.setDeptList(deptList);
            }
        }
        return deptVO;
    }


    /**
     * 返回树形菜单集合
     *
     * @return 树形菜单
     */
    @GetMapping(value = "/tree")
    public List<DeptTree> getTree() {
        SysDept condition = new SysDept();
        condition.setDelFlag(CommonConstant.STATUS_NORMAL);
        List<DeptTree> deptTrees = sysDeptService.selectListTree(new EntityWrapper<>(condition));
        return deptTrees;
    }

    /**
     * 添加
     *
     * @param sysDept 实体
     * @return success/false
     */
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_dept_add')")
    public R<Boolean> add(@RequestBody SysDept sysDept) {
        String user = SecurityUtils.getUser();
        sysDept.setCreateBy(user);
        return new R<>(sysDeptService.insertDept(sysDept));
    }

    /**
     * 删除
     *
     * @param id ID
     * @return success/false
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('sys_dept_del')")
    public R<Boolean> delete(@PathVariable Integer id) {
        return new R<>(sysDeptService.deleteDeptById(id));
    }

    /**
     * 编辑
     *
     * @param sysDept 实体
     * @return success/false
     */
    @PutMapping
    @PreAuthorize("@pms.hasPermission('sys_dept_edit')")
    public Boolean edit(@RequestBody SysDept sysDept) {
        sysDept.setUpdateTime(LocalDateTime.now());
        String user = SecurityUtils.getUser();
        sysDept.setUpdateBy(user);
        return sysDeptService.updateDeptById(sysDept);
    }

    /**
     * 　　* @Description: 根据部门编码查询列表
     * 　　* @param
     * 　　* @return
     * 　　* @author lmh
     * 　　* @date 2019/1/22 11:20
     */
    @GetMapping("/page")
    public Page page(@RequestParam Map<String, Object> params) {
        Page page = sysDeptService.selectWithDeptPage(new Query(params));
        return page;
    }
}
