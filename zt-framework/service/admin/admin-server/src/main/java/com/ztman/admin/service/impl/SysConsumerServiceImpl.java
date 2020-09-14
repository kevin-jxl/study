package com.ztman.admin.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.admin.api.entity.SysConsumer;
import com.ztman.admin.api.vo.SysConsumerVO;
import com.ztman.admin.mapper.SysConsumerMapper;
import com.ztman.admin.service.SysConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import cn.hutool.core.bean.BeanUtil;
import com.ztman.common.core.util.Query;

import java.util.List;


@Service("sysConsumerService")
public class SysConsumerServiceImpl extends ServiceImpl<SysConsumerMapper, SysConsumer> implements SysConsumerService {

    @Autowired
    private SysConsumerMapper sysConsumerMapper;

    /**
     * 查询用户管理信息
     *
     * @param userId 用户管理ID
     * @return 用户管理信息
     */
    @Override
    public SysConsumerVO selectSysConsumerById(Integer userId)
    {
        return sysConsumerMapper.selectSysConsumerById(userId);
    }

    /**
     * 查询用户管理列表
     *
     * @param sysConsumer 用户管理信息
     * @return 用户管理集合
     */
    @Override
    public List<SysConsumer> selectSysConsumerList(SysConsumer sysConsumer)
    {
        return sysConsumerMapper.selectSysConsumerList(sysConsumer);
    }


    /**
     * 分页模糊查询用户管理列表
     * @return 用户管理集合
     */
    @Override
    public Page selectSysConsumerListByLike(Query query)
    {
        SysConsumerVO sysConsumer =  BeanUtil.mapToBean(query.getCondition(), SysConsumerVO.class,false);
        if(sysConsumer != null && sysConsumer.getUserRealname()!=null){
            sysConsumer.setUserRealname(sysConsumer.getUserRealname().trim());
        }

        query.setRecords(sysConsumerMapper.selectSysConsumerListByLike(query, sysConsumer));
        return query;
    }

    /**
     * 新增用户管理
     *
     * @param sysConsumer 用户管理信息
     * @return 结果
     */
    @Override
    public int insertSysConsumer(SysConsumer sysConsumer)
    {
        return sysConsumerMapper.insertSysConsumer(sysConsumer);
    }

    /**
     * 修改用户管理
     *
     * @param sysConsumer 用户管理信息
     * @return 结果
     */
    @Override
    public int updateSysConsumer(SysConsumer sysConsumer)
    {
        return sysConsumerMapper.updateSysConsumer(sysConsumer);
    }


    /**
     * 删除用户管理
     *
     * @param userId 用户管理ID
     * @return 结果
     */
    @Override
    public int deleteSysConsumerById(Integer userId)
    {
        return sysConsumerMapper.deleteSysConsumerById( userId);
    };


    /**
     * 批量删除用户管理对象
     *
     * @param userIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysConsumerByIds(String[] userIds)
    {
        return sysConsumerMapper.deleteSysConsumerByIds( userIds);
    }

    @Override
    public List<Integer> getALlCompany(int id) {
        return sysConsumerMapper.getAllCompany(id);
    }
}
