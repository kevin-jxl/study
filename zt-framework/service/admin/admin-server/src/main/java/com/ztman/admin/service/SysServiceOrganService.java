package com.ztman.admin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ztman.admin.api.entity.SysServiceOrgan;
import com.ztman.admin.api.vo.SysServiceOrganVO;
import com.ztman.common.core.util.Query;

import java.util.HashMap;
import java.util.List;

/**
 * 业务机构表 服务接口层
 *
 * @author ztman code generator
 * @date 2020-08-25 19:44:47
 */
public interface SysServiceOrganService extends IService<SysServiceOrgan> {
    /**
     * 查询业务机构表信息
     *
     * @param serviceId 业务机构表ID
     * @return 业务机构表信息
     */
    public SysServiceOrganVO selectSysServiceOrganById(Integer serviceId);

    /**
     * 查询业务机构表列表
     *
     * @param sysServiceOrgan 业务机构表信息
     * @return 业务机构表集合
     */
    public List<SysServiceOrgan> selectSysServiceOrganList(SysServiceOrgan sysServiceOrgan);


    /**
     * 分页模糊查询业务机构表列表
     * @return 业务机构表集合
     */
    public Page selectSysServiceOrganListByLike(Query query);



    /**
     * 新增业务机构表
     *
     * @param sysServiceOrgan 业务机构表信息
     * @return 结果
     */
    public int insertSysServiceOrgan(SysServiceOrgan sysServiceOrgan);

    /**
     * 修改业务机构表
     *
     * @param sysServiceOrgan 业务机构表信息
     * @return 结果
     */
    public int updateSysServiceOrgan(SysServiceOrgan sysServiceOrgan);

    /**
     * 删除业务机构表
     *
     * @param serviceId 业务机构表ID
     * @return 结果
     */
    public int deleteSysServiceOrganById(Integer serviceId);

    /**
     * 批量删除业务机构表
     *
     * @param serviceIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysServiceOrganByIds(String[] serviceIds);

    String selectOrganIdsByUserId(Integer userId);

    Page selectSysServiceOrganListByLikeOrgan(Query objectQuery);

    List<HashMap<String, Object>> inputList(Query query);

    List<HashMap<String, Object>> list(String organId);
}

