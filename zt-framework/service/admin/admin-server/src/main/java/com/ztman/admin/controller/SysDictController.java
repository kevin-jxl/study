package com.ztman.admin.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.entity.SysDict;
import com.ztman.admin.api.entity.SysDictIndex;
import com.ztman.admin.api.vo.SysDictVO;
import com.ztman.admin.service.SysDictIndexService;
import com.ztman.admin.service.SysDictService;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import com.ztman.common.log.annotation.SysLog;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典明细
 *
 * @author spark code generator
 * @date 2019-03-08 13:39:56
 */
@RestController
@RequestMapping("/sysdict")
public class SysDictController {
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private SysDictIndexService sysDictIndexService;


    /**
     * 列表
     *
     * @param params
     * @return
     */
    @GetMapping("/page")
    public Page page(@RequestParam Map<String, Object> params) {
        //数据库字段值完整查询
        // return  sysDictService.selectPage(new Query<>(params), new EntityWrapper<>());
        //对象模糊查询
        return sysDictService.selectSysDictListByLike(new Query<>(params));
    }


    /**
     * 信息
     *
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Integer id) {
        SysDict sysDict = sysDictService.selectById(id);
        return new R<>(sysDict);
    }

    /**
     * 保存
     *
     * @param sysDictVO
     * @return R
     */
    @PreAuthorize("@pms.hasPermission('sys_dict_add')")
    @SysLog("字典类型增加")
    @PostMapping
    public R save(@RequestBody SysDictVO sysDictVO) {
        try {
            SysDict sysDict = new SysDict();
            BeanUtils.copyProperties(sysDict, sysDictVO);
            sysDictService.insert(sysDict);
            sysDictService.insetDictRelevance(sysDictVO.getDictIndexId(), sysDict.getId());
            return new R<>(Boolean.TRUE);
        } catch (Exception e) {
            return new R<>(Boolean.FALSE);
        }
    }

    /**
     * 修改
     *
     * @param sysDictVO
     * @return R
     */
    @PreAuthorize("@pms.hasPermission('sys_dict_edit')")
    @SysLog("字典类型修改")
    @PutMapping
    public R update(@RequestBody SysDictVO sysDictVO) {
        try {
            SysDict sysDict = new SysDict();
            BeanUtils.copyProperties(sysDict, sysDictVO);
            sysDictService.updateById(sysDict);
            return new R<>(Boolean.TRUE);
        } catch (Exception e) {
            return new R<>(Boolean.FALSE);
        }
    }


    /**
     * 删除
     *
     * @param id
     * @return R
     */
    @PreAuthorize("@pms.hasPermission('sys_dict_del')")
    @SysLog("字典类型删除")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        sysDictService.deleteById(id);
        return new R<>(Boolean.TRUE);
    }

    /**
     * 根据字典查询字典明细
     *
     * @param dictId
     * @return
     */
    @GetMapping("/selectDicts")
    public List selectDicts(@RequestParam("dictId") Integer dictId) {
        List list = sysDictService.selectDicts(dictId);
        return list;
    }

    /**
     * 通用字典分类查询
     *
     * @param searchType 查询分类 0 查询字典值 1查询字典明细
     * @param searchCode 根据字典值查询字典 或者查询字典明细列表
     * @return
     */
    @GetMapping("/selectDictByType")
    public Map selectDictByType(@RequestParam(required = true) String searchType,
                                @RequestParam(required = true) String searchCode) {
        Map<String, Object> result = new HashMap<>();
        SysDictIndex sysDictIndex = new SysDictIndex();
        sysDictIndex.setCode(searchCode);
        sysDictIndex.setDelFlag("1");
        EntityWrapper<SysDictIndex> wrapper = new EntityWrapper<SysDictIndex>(sysDictIndex);
        SysDictIndex selectOne = sysDictIndexService.selectOne(wrapper);
        if (selectOne != null) {
            if ("0".equals(searchType)) {
                result.put("dictObj", selectOne);
            } else {
                List<SysDict> list = sysDictService.selectDicts(selectOne.getId());
                result.put("dictList", list);
            }
        }
        return result;
    }
}
