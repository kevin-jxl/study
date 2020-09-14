package com.ztman.admin.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.entity.SysDictIndex;
import com.ztman.admin.api.vo.SysDictIndexVO;
import com.ztman.admin.service.SysDictIndexService;
import com.ztman.common.core.util.ExcelUtil;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 字典
 *
 * @author spark code generator
 * @date 2019-03-08 10:41:04
 */
@RestController
@RequestMapping("/sysdictindex")
public class SysDictIndexController {
    @Autowired
    private SysDictIndexService sysDictIndexService;

    //excel工具类
    ExcelUtil util = new ExcelUtil();

    /**
     * 列表
     *
     * @param params
     * @return
     */
    @GetMapping("/page")
    public Page page(@RequestParam Map<String, Object> params) {
        //数据库字段值完整查询
        // return  sysDictIndexService.selectPage(new Query<>(params), new EntityWrapper<>());
        //对象模糊查询
        return sysDictIndexService.selectSysDictIndexListByLike(new Query<>(params));
    }


    /**
     * 信息
     *
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Integer id) {
        SysDictIndex sysDictIndex = sysDictIndexService.selectById(id);
        return new R<>(sysDictIndex);
    }

    /**
     * 保存
     *
     * @param sysDictIndex
     * @return R
     */
    @PostMapping
    public R save(@RequestBody SysDictIndex sysDictIndex) {
        sysDictIndex.setDelFlag("1");
        sysDictIndexService.insert(sysDictIndex);
        return new R<>(Boolean.TRUE);
    }

    /**
     * 修改
     *
     * @param sysDictIndex
     * @return R
     */
    @PutMapping
    public R update(@RequestBody SysDictIndex sysDictIndex) {
        sysDictIndexService.updateById(sysDictIndex);
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
        sysDictIndexService.deleteById(id);
        return new R<>(Boolean.TRUE);
    }

    /**
     * 根据分类ID查询字典列表
     *
     * @param typeId
     * @return
     */
    @GetMapping("/selectDictIndex")
    public List selectDictIndex(@RequestParam(required = true, value = "typeId") Integer typeId) {
        List list = sysDictIndexService.selectDictIndex(typeId);
        return list;
    }

    //下载Excel
    @GetMapping("/downloadExcel")
    public void downloadExcel(HttpServletResponse res, @RequestParam Map<String, Object> params) throws IOException {

        List<SysDictIndexVO> records = page(params).getRecords();
        String[] headInfo = {"序号", "字典名称", "字典Code", "创建人", "创建时间"};
        String[] sheetName = {"字dian表"};
        util.createWorkBook(ExcelUtil.EXCELVERSION.EXCEL_VERSION_2007, sheetName);
        //创建第一行标题头
        List<String[]> list2 = new ArrayList<String[]>();
        list2.add(headInfo);

        List<Integer[]> alist = new ArrayList<>();
        Integer[] a1 = {0, 0, 0, headInfo.length};//开始行,结束行,开始列,结束列
        alist.add(a1);
        List<Integer[]> blist = new ArrayList<>();

        util.setValue(list2, util.getCellType4Merge(0, 10, blist), 0, 0);
        for (int i = 0; i < records.size(); i ++) {
            SysDictIndexVO obj = records.get(i);
            List<String[]> cacheList = new ArrayList<String[]>();
            String[] tb = new String[headInfo.length];
            int index = 0;
            tb[index++] = String.valueOf(i + 1);
            tb[index++] = obj.getName();
            tb[index++] = obj.getCode();
            tb[index++] = obj.getCreateName();
            tb[index++] = obj.getCreateTime().toLocaleString();
            cacheList.add(tb);
            util.setValue(cacheList, util.getCellType4Number(), 0, i + 1);
        }
        util.exportExcel(res, "字典");
    }
}
