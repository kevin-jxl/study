package com.ztman.admin.controller;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ztman.admin.api.entity.Studennew;
import com.ztman.admin.service.StudennewService;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import com.ztman.common.data.datascope.DataScope;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.Map;


/**
 * 学生表测试
 *
 * @author ztman code generator
 * @date 2019-01-21 19:02:11
 */
@RestController
@RequestMapping("/studennew")
public class StudennewController {
    @Autowired
    private StudennewService studennewService;


    /**
     *  列表
     * @param params
     * @return
     */
    @GetMapping("/page")
    public Page page(@RequestParam Map<String, Object> params) {
        return  studennewService.selectPage(new Query<>(params), new EntityWrapper<>());
    }

    /**
     * 获取学生列表By 数据权限
     */
    @GetMapping("/listByScope")
    @ApiOperation("根据数据权限查询学生列表")
    public   List<Studennew> listByScope() {
        //5.测试查询--列表
        List<Studennew>  ss=studennewService.selectStudennewList(new Studennew(),new DataScope());
        return ss;
    }

    /**
     * 获取学生列表
     */
    @GetMapping("/list")
    @ApiOperation("无数据权限查询学生列表")
    public   List<Studennew> list() {
        //5.测试查询--列表
        List<Studennew>  ss=studennewService.selectStudennewList(new Studennew());
        return ss;
    }
    /**
     * 信息
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Integer id){
        Studennew studennew = studennewService.selectById(id);
        return new R<>(studennew);
    }

    /**
     * 保存
     * @param studennew
     * @return R
     */
    @PostMapping
    public R save(@RequestBody Studennew studennew){
        studennewService.insert(studennew);
        return new R<>(Boolean.TRUE);
    }

    /**
     * 修改
     * @param studennew
     * @return R
     */
    @PutMapping
    public R update(@RequestBody Studennew studennew){
        studennewService.updateById(studennew);
        return new R<>(Boolean.TRUE);
    }




    /**
     * 删除
     * @param id
     * @return R
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable  Integer id){
        studennewService.deleteById(id);
        return new R<>(Boolean.TRUE);
    }
    /**
     * 综合测试
     */
    @GetMapping("/doSomthing")
    public R doSomthing(){
        //1.测试新增
        Studennew s=new Studennew();
        s.setName("t3");
        int result_insert= studennewService.insertStudennew(s);
        System.out.println("新增结果："+result_insert);
        //2.测试删除
        int result_delete=  studennewService.deleteStudennewById(2);
        //3.测试修改
        s.setId(1);
        int result_update=studennewService.updateStudennew(s);
        //4.测试查询--主键
        Studennew  t3= studennewService.selectStudennewById(1);
        //5.测试查询--列表
        List<Studennew>  ss=studennewService.selectStudennewList(new Studennew(),new DataScope());
        System.out.println("数据大小："+ss.size());
        return new R<>(Boolean.TRUE);

    }

}
