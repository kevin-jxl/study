package com.ztman.admin.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.entity.SysPosdummy;
import com.ztman.admin.service.SysPosdummyService;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 虚拟岗位表
 *
 * @author spark code generator
 * @date 2019-02-22 14:43:22
 */
@RestController
@RequestMapping("/sysposdummy")
public class SysPosdummyController {
    @Autowired
    private SysPosdummyService sysPosdummyService;


    /**
     * 列表
     *
     * @param params
     * @return
     */
    @GetMapping("/page")
    public Page page(@RequestParam Map<String, Object> params) {
        //数据库字段值完整查询
        // return  sysPosdummyService.selectPage(new Query<>(params), new EntityWrapper<>());
        //对象模糊查询
        return sysPosdummyService.selectSysPosdummyListByLike(new Query<>(params));
    }


    /**
     * 信息
     *
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Integer id) {
        SysPosdummy sysPosdummy = sysPosdummyService.selectById(id);
        return new R<>(sysPosdummy);
    }

    /**
     * 保存
     *
     * @param sysPosdummy
     * @return R
     */
    @PostMapping
    public R save(@RequestBody SysPosdummy sysPosdummy) {
        sysPosdummyService.insert(sysPosdummy);
        return new R<>(Boolean.TRUE);
    }

    /**
     * 修改
     *
     * @param sysPosdummy
     * @return R
     */
    @PutMapping
    public R update(@RequestBody SysPosdummy sysPosdummy) {
        sysPosdummyService.updateById(sysPosdummy);
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
        sysPosdummyService.deleteById(id);
        return new R<>(Boolean.TRUE);
    }

    /**
     * 设置虚拟岗位标签权限
     *
     * @param posdummyId
     * @param powerIds
     * @return
     */
    @GetMapping("/posDummyPowers")
    public R posDummyPowers(@RequestParam() Integer posdummyId,
                            @RequestParam() String powerIds) {
        sysPosdummyService.posDummyPowers(posdummyId, powerIds);
        return new R<>(Boolean.TRUE);
    }


    /**
     * 列表不分页
     *
     * @param params
     * @return
     */
    @GetMapping("/sysPosdummyList")
    public Map sysPowerLabelList(@RequestParam Map<String, Object> params) {

        return sysPosdummyService.sysPosdummyList(params);
    }
}
