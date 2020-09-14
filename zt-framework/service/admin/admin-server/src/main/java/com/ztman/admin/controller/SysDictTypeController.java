package com.ztman.admin.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.dto.DictTree;
import com.ztman.admin.api.entity.SysDictType;
import com.ztman.common.core.constant.CommonConstant;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 字典分类
 *
 * @author spark code generator
 * @date 2019-03-08 09:40:04
 */
@RestController
@RequestMapping("/sysdicttype")
public class SysDictTypeController {
    @Autowired
    private com.ztman.admin.service.SysDictTypeService SysDictTypeService;


    /**
     * 列表
     *
     * @param params
     * @return
     */
    @GetMapping("/page")
    public Page page(@RequestParam Map<String, Object> params) {
        //数据库字段值完整查询
        // return  SysDictTypeService.selectPage(new Query<>(params), new EntityWrapper<>());
        //对象模糊查询
        return SysDictTypeService.selectSysDictTypeListByLike(new Query<>(params));
    }


    /**
     * 信息
     *
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Integer id) {
        SysDictType SysDictType = SysDictTypeService.selectById(id);
        return new R<>(SysDictType);
    }

    /**
     * 保存
     *
     * @param SysDictType
     * @return R
     */
    @PostMapping
    public R save(@RequestBody SysDictType SysDictType) {
        SysDictType.setDelFlag("1");
        SysDictTypeService.insert(SysDictType);
        return new R<>(Boolean.TRUE);
    }

    /**
     * 修改
     *
     * @param SysDictType
     * @return R
     */
    @PutMapping
    public R update(@RequestBody SysDictType SysDictType) {
        SysDictTypeService.updateById(SysDictType);
        return new R<>(Boolean.TRUE);
    }


    /**
     * 删除
     *
     * @param id
     * @return R
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        SysDictTypeService.deleteById(id);
        return new R<>(Boolean.TRUE);
    }

    /**
     * 获取字典树形菜单
     *
     * @return
     */
    @GetMapping(value = "/tree")
    public List<DictTree> getTree() {
        SysDictType condition = new SysDictType();
        condition.setDelFlag(CommonConstant.STATUS_NORMAL);
        List<DictTree> dictTrees = SysDictTypeService.selectListTree(new EntityWrapper<>(condition));
        return dictTrees;
    }
}
