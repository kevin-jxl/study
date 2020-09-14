package com.ztman.admin.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.entity.SysPowerLabel;
import com.ztman.admin.service.SysPowerLabelService;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author spark code generator
 * @date 2019-02-22 14:43:28
 */
@RestController
@RequestMapping("/syspowerlabel")
public class SysPowerLabelController {
    @Autowired
    private SysPowerLabelService sysPowerLabelService;

    /**
     * 列表
     *
     * @param params
     * @return
     */
    @GetMapping("/page")
    public Page page(@RequestParam Map<String, Object> params) {
        //数据库字段值完整查询
        // return  sysPowerLabelService.selectPage(new Query<>(params), new EntityWrapper<>());
        //对象模糊查询
        return sysPowerLabelService.selectSysPowerLabelListByLike(new Query<>(params));
    }

    /**
     * 列表不分页
     *
     * @param params
     * @return
     */
    @GetMapping("/sysPowerLabelList")
    public Map sysPowerLabelList(@RequestParam Map<String, Object> params) {

        return sysPowerLabelService.sysPowerLabelList(params);
    }


    /**
     * 信息
     *
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Integer id) {
        SysPowerLabel sysPowerLabel = sysPowerLabelService.selectById(id);
        return new R<>(sysPowerLabel);
    }

    /**
     * 保存
     *
     * @param sysPowerLabel
     * @return R
     */
    @PostMapping
    public R save(@RequestBody SysPowerLabel sysPowerLabel) {
        sysPowerLabelService.insert(sysPowerLabel);
        return new R<>(Boolean.TRUE);
    }

    /**
     * 修改
     *
     * @param sysPowerLabel
     * @return R
     */
    @PutMapping
    public R update(@RequestBody SysPowerLabel sysPowerLabel) {
        sysPowerLabelService.updateById(sysPowerLabel);
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
        sysPowerLabelService.deleteById(id);
        return new R<>(Boolean.TRUE);
    }
}
