package com.ztman.admin.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.entity.SysSystemIntegration;
import com.ztman.admin.api.vo.SysSystemIntegrationVO;
import com.ztman.common.core.util.Query;

import java.util.List;

/**
 * 1、系统集成、2、物联网集成 服务接口层
 *
 * @author ztman code generator
 * @date 2020-09-07 16:06:12
 */
public interface SysSystemIntegrationService extends IService<SysSystemIntegration> {
    /**
     * 查询1、系统集成、2、物联网集成信息
     *
     * @param systemId 1、系统集成、2、物联网集成ID
     * @return 1、系统集成、2、物联网集成信息
     */
    public SysSystemIntegration selectSysSystemIntegrationById(Integer systemId);

    /**
     * 查询1、系统集成、2、物联网集成列表
     *
     * @param sysSystemIntegration 1、系统集成、2、物联网集成信息
     * @return 1、系统集成、2、物联网集成集合
     */
    public List<SysSystemIntegration> selectSysSystemIntegrationList(SysSystemIntegration sysSystemIntegration);


    /**
     * 分页模糊查询1、系统集成、2、物联网集成列表
     * @return 1、系统集成、2、物联网集成集合
     */
    public Page selectSysSystemIntegrationListByLike(Query query);



    /**
     * 新增1、系统集成、2、物联网集成
     *
     * @param sysSystemIntegration 1、系统集成、2、物联网集成信息
     * @return 结果
     */
    public int insertSysSystemIntegration(SysSystemIntegration sysSystemIntegration);

    /**
     * 修改1、系统集成、2、物联网集成
     *
     * @param sysSystemIntegration 1、系统集成、2、物联网集成信息
     * @return 结果
     */
    public int updateSysSystemIntegration(SysSystemIntegration sysSystemIntegration);

    /**
     * 删除1、系统集成、2、物联网集成
     *
     * @param systemId 1、系统集成、2、物联网集成ID
     * @return 结果
     */
    public int deleteSysSystemIntegrationById(Integer systemId);

    /**
     * 批量删除1、系统集成、2、物联网集成
     *
     * @param systemIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysSystemIntegrationByIds(String[] systemIds);


    List<SysSystemIntegrationVO> selectSysSystemIntegrationListJob(SysSystemIntegration integration);
}

