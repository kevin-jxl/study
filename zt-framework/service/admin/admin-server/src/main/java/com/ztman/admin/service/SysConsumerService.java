package com.ztman.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.entity.SysConsumer;
import com.ztman.admin.api.vo.SysConsumerVO;
import com.ztman.common.core.util.Query;

import java.util.List;

/**
 * 用户管理 服务接口层
 *
 * @author shuda
 * @date 2020-08-21 16:21:27
 */
public interface SysConsumerService extends IService<SysConsumer> {
    /**
     * 查询用户管理信息
     *
     * @param userId 用户管理ID
     * @return 用户管理信息
     */
    public SysConsumerVO selectSysConsumerById(Integer userId);

    /**
     * 查询用户管理列表
     *
     * @param sysConsumer 用户管理信息
     * @return 用户管理集合
     */
    public List<SysConsumer> selectSysConsumerList(SysConsumer sysConsumer);


    /**
     * 分页模糊查询用户管理列表
     * @return 用户管理集合
     */
    public Page selectSysConsumerListByLike(Query query);



    /**
     * 新增用户管理
     *
     * @param sysConsumer 用户管理信息
     * @return 结果
     */
    public int insertSysConsumer(SysConsumer sysConsumer);

    /**
     * 修改用户管理
     *
     * @param sysConsumer 用户管理信息
     * @return 结果
     */
    public int updateSysConsumer(SysConsumer sysConsumer);

    /**
     * 删除用户管理
     *
     * @param userId 用户管理ID
     * @return 结果
     */
    public int deleteSysConsumerById(Integer userId);

    /**
     * 批量删除用户管理
     *
     * @param userIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysConsumerByIds(String[] userIds);

    List<Integer> getALlCompany(int id);
}

