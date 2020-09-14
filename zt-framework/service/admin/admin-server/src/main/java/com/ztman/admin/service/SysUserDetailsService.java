package com.ztman.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.entity.SysUserDetails;
import com.ztman.common.core.util.Query;

import java.util.List;

/**
 * 用户关联信息表 服务接口层
 *
 * @author ztman code generator
 * @date 2020-08-24 09:14:21
 */
public interface SysUserDetailsService extends IService<SysUserDetails> {
    /**
     * 查询用户关联信息表信息
     *
     * @param userId 用户关联信息表ID
     * @return 用户关联信息表信息
     */
    public SysUserDetails selectSysUserDetailsById(Integer userId);

    /**
     * 查询用户关联信息表列表
     *
     * @param sysUserDetails 用户关联信息表信息
     * @return 用户关联信息表集合
     */
    public List<SysUserDetails> selectSysUserDetailsList(SysUserDetails sysUserDetails);


    /**
     * 分页模糊查询用户关联信息表列表
     * @return 用户关联信息表集合
     */
    public Page selectSysUserDetailsListByLike(Query query);



    /**
     * 新增用户关联信息表
     *
     * @param sysUserDetails 用户关联信息表信息
     * @return 结果
     */
    public int insertSysUserDetails(SysUserDetails sysUserDetails);

    /**
     * 修改用户关联信息表
     *
     * @param sysUserDetails 用户关联信息表信息
     * @return 结果
     */
    public int updateSysUserDetails(SysUserDetails sysUserDetails);

    /**
     * 删除用户关联信息表
     *
     * @param userId 用户关联信息表ID
     * @return 结果
     */
    public int deleteSysUserDetailsById(Integer userId);

    /**
     * 批量删除用户关联信息表
     *
     * @param userIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysUserDetailsByIds(String[] userIds);

}

