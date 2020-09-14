package com.ztman.admin.service.impl;

import com.ztman.admin.api.entity.SysPost;
import com.ztman.admin.mapper.SysPostMapper;
import com.ztman.admin.mapper.SysUserMapper;
import com.ztman.admin.service.SysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import cn.hutool.core.bean.BeanUtil;
import com.ztman.common.core.util.Query;


@Service("sysPostService")
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {

    @Autowired
    private SysPostMapper sysPostMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 查询岗位表信息
     *
     * @param id 岗位表ID
     * @return 岗位表信息
     */
    @Override
    public SysPost selectSysPostById(Integer id) {
        return sysPostMapper.selectSysPostById(id);
    }

    /**
     * 查询岗位表列表
     *
     * @param sysPost 岗位表信息
     * @return 岗位表集合
     */
    @Override
    public List<SysPost> selectSysPostList(SysPost sysPost) {
        return sysPostMapper.selectSysPostList(sysPost);
    }


    /**
     * 分页模糊查询岗位表列表
     *
     * @return 岗位表集合
     */
    @Override
    public Page selectSysPostListByLike(Query query) {
        SysPost sysPost = BeanUtil.mapToBean(query.getCondition(), SysPost.class, false);
        query.setRecords(sysPostMapper.selectSysPostListByLike(query, sysPost));
        return query;
    }

    /**
     * 新增岗位表
     *
     * @param sysPost 岗位表信息
     * @return 结果
     */
    @Override
    public int insertSysPost(SysPost sysPost) {
        return sysPostMapper.insertSysPost(sysPost);
    }

    /**
     * 修改岗位表
     *
     * @param sysPost 岗位表信息
     * @return 结果
     */
    @Override
    public int updateSysPost(SysPost sysPost) {
        return sysPostMapper.updateSysPost(sysPost);
    }


    /**
     * 删除岗位表
     *
     * @param id 岗位表ID
     * @return 结果
     */
    public int deleteSysPostById(Integer id) {
        return sysPostMapper.deleteSysPostById(id);
    }

    ;


    /**
     * 批量删除岗位表对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysPostByIds(String[] ids) {
        return sysPostMapper.deleteSysPostByIds(ids);
    }

    /**
     * 根据岗位查询用户
     *
     * @param posts
     * @return
     */
    @Override
    public List selectUserByPost(List<Integer> posts) {
        return sysUserMapper.selectUserByPost(posts);
    }

}
