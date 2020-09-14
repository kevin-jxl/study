package com.ztman.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.entity.SysPost;
import com.ztman.common.core.util.Query;

import java.util.List;

/**
 * 岗位表  mapper层
 *
 * @author ztman code generator
 * @date 2019-01-24 18:43:26
 */
public interface SysPostMapper extends BaseMapper<SysPost> {
    /**
     * 查询岗位表信息
     *
     * @param id 岗位表ID
     * @return 岗位表信息
     */
    public SysPost selectSysPostById(Integer id);

    /**
     * 查询岗位表列表
     *
     * @param sysPost 岗位表信息
     * @return 岗位表集合
     */
    public List<SysPost> selectSysPostList(SysPost sysPost);

    /**
     * 模糊查询岗位表列表
     *
     * @param sysPost 岗位表信息
     * @return 岗位表集合
     */
    public List<SysPost> selectSysPostListByLike(SysPost sysPost);


    /**
     * 分页模糊查询岗位表列表
     *
     * @param sysPost 岗位表信息
     * @return 岗位表集合
     */
    public List<SysPost> selectSysPostListByLike(Query query, SysPost sysPost);


    /**
     * 新增岗位表
     *
     * @param sysPost 岗位表信息
     * @return 结果
     */
    public int insertSysPost(SysPost sysPost);

    /**
     * 修改岗位表
     *
     * @param sysPost 岗位表信息
     * @return 结果
     */
    public int updateSysPost(SysPost sysPost);

    /**
     * 删除岗位表
     *
     * @param id 岗位表ID
     * @return 结果
     */
    public int deleteSysPostById(Integer id);

    /**
     * 批量删除岗位表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysPostByIds(String[] ids);



}
