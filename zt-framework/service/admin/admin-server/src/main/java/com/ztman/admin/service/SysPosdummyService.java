package com.ztman.admin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ztman.admin.api.entity.SysPosdummy;
import com.ztman.common.core.util.Query;

import java.util.List;
import java.util.Map;

/**
 * 虚拟岗位表 服务接口层
 *
 * @author ztman code generator
 * @date 2019-02-22 14:43:22
 */
public interface SysPosdummyService extends IService<SysPosdummy> {
    /**
     * 查询虚拟岗位表信息
     *
     * @param id 虚拟岗位表ID
     * @return 虚拟岗位表信息
     */
    public SysPosdummy selectSysPosdummyById(Integer id);

    /**
     * 查询虚拟岗位表列表
     *
     * @param sysPosdummy 虚拟岗位表信息
     * @return 虚拟岗位表集合
     */
    public List<SysPosdummy> selectSysPosdummyList(SysPosdummy sysPosdummy);


    /**
     * 分页模糊查询虚拟岗位表列表
     * @return 虚拟岗位表集合
     */
    public Page selectSysPosdummyListByLike(Query query);



    /**
     * 新增虚拟岗位表
     *
     * @param sysPosdummy 虚拟岗位表信息
     * @return 结果
     */
    public int insertSysPosdummy(SysPosdummy sysPosdummy);

    /**
     * 修改虚拟岗位表
     *
     * @param sysPosdummy 虚拟岗位表信息
     * @return 结果
     */
    public int updateSysPosdummy(SysPosdummy sysPosdummy);

    /**
     * 删除虚拟岗位表
     *
     * @param id 虚拟岗位表ID
     * @return 结果
     */
    public int deleteSysPosdummyById(Integer id);

    /**
     * 批量删除虚拟岗位表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysPosdummyByIds(String[] ids);

    void posDummyPowers(Integer posdummyId, String powerIds);


    Map sysPosdummyList(Map<String, Object> params);
}

