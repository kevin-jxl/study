package com.ztman.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.admin.api.entity.SysUserDetails;
import com.ztman.admin.mapper.SysUserDetailsMapper;
import com.ztman.admin.service.SysUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import cn.hutool.core.bean.BeanUtil;
import com.ztman.common.core.util.Query;

import java.util.List;


@Service("sysUserDetailsService")
public class SysUserDetailsServiceImpl extends ServiceImpl<SysUserDetailsMapper, SysUserDetails> implements SysUserDetailsService {

    @Autowired
    private SysUserDetailsMapper sysUserDetailsMapper;

    /**
     * 查询用户关联信息表信息
     *
     * @param userId 用户关联信息表ID
     * @return 用户关联信息表信息
     */
    @Override
    public SysUserDetails selectSysUserDetailsById(Integer userId)
    {
        return sysUserDetailsMapper.selectSysUserDetailsById(userId);
    }

    /**
     * 查询用户关联信息表列表
     *
     * @param sysUserDetails 用户关联信息表信息
     * @return 用户关联信息表集合
     */
    @Override
    public List<SysUserDetails> selectSysUserDetailsList(SysUserDetails sysUserDetails)
    {
        return sysUserDetailsMapper.selectSysUserDetailsList(sysUserDetails);
    }


    /**
     * 分页模糊查询用户关联信息表列表
     * @return 用户关联信息表集合
     */
    @Override
    public Page selectSysUserDetailsListByLike(Query query)
    {
        SysUserDetails sysUserDetails =  BeanUtil.mapToBean(query.getCondition(), SysUserDetails.class,false);
        query.setRecords(sysUserDetailsMapper.selectSysUserDetailsListByLike(query,sysUserDetails));
        return query;
    }

    /**
     * 新增用户关联信息表
     *
     * @param sysUserDetails 用户关联信息表信息
     * @return 结果
     */
    @Override
    public int insertSysUserDetails(SysUserDetails sysUserDetails)
    {
        return sysUserDetailsMapper.insertSysUserDetails(sysUserDetails);
    }

    /**
     * 修改用户关联信息表
     *
     * @param sysUserDetails 用户关联信息表信息
     * @return 结果
     */
    @Override
    public int updateSysUserDetails(SysUserDetails sysUserDetails)
    {
        return sysUserDetailsMapper.updateSysUserDetails(sysUserDetails);
    }


    /**
     * 删除用户关联信息表
     *
     * @param userId 用户关联信息表ID
     * @return 结果
     */
    public int deleteSysUserDetailsById(Integer userId)
    {
        return sysUserDetailsMapper.deleteSysUserDetailsById( userId);
    };


    /**
     * 批量删除用户关联信息表对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysUserDetailsByIds(String[] userIds)
    {
        return sysUserDetailsMapper.deleteSysUserDetailsByIds( userIds);
    }

}
