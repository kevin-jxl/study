package com.ztman.admin.controller;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ztman.admin.api.entity.SysPost;
import com.ztman.admin.api.entity.SysUser;
import com.ztman.admin.service.SysPostService;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.Map;


/**
 * 岗位表
 *
 * @author ztman code generator
 * @date 2019-01-24 18:43:26
 */
@RestController
@RequestMapping("/syspost")
public class PostController {
    @Autowired
    private SysPostService sysPostService;


    /**
     * 列表
     *
     * @param params
     * @return
     */
    @GetMapping("/page")
    public Page page(@RequestParam Map<String, Object> params) {
        //数据库字段值完整查询
        // return  sysPostService.selectPage(new Query<>(params), new EntityWrapper<>());
        //对象模糊查询
        return sysPostService.selectSysPostListByLike(new Query<>(params));
    }

    /**
     * 获取岗位列表
     *
     * @return 角色列表
     */
    @GetMapping(value = {"/postList"})
    public List<SysPost> postList() {
        return sysPostService.selectList(new EntityWrapper<>());

    }

    /**
     * 信息
     *
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Integer id) {
        SysPost sysPost = sysPostService.selectById(id);
        return new R<>(sysPost);
    }

    /**
     * 保存
     *
     * @param sysPost
     * @return R
     */
    @PostMapping
    public R save(@RequestBody SysPost sysPost) {
        sysPostService.insert(sysPost);
        return new R<>(Boolean.TRUE);
    }

    /**
     * 修改
     *
     * @param sysPost
     * @return R
     */
    @PutMapping
    public R update(@RequestBody SysPost sysPost) {
        sysPostService.updateById(sysPost);
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
        sysPostService.deleteById(id);
        return new R<>(Boolean.TRUE);
    }


    /**
     * 根据岗位查询用户
     *
     * @param posts
     * @return
     */
    @PostMapping(value = {"/postList"})
    public List<SysUser> selectUserByPost(@RequestParam("posts") String posts) {
        List list = new ArrayList();
        List<Integer> poList = new ArrayList<>();
        String[] posArr=posts.split(",");
        for(String pos:posArr){
            poList.add(Integer.valueOf(pos));
        }
        if (poList != null && poList.size() > 0) {
            list = sysPostService.selectUserByPost(poList);
        }
        return list;
    }
}
