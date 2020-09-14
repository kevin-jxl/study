package com.ztman.admin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ztman.admin.api.entity.SysAdministrativeOrgan;
import com.ztman.admin.api.vo.AdministrativeOrganVO;
import com.ztman.common.core.util.Query;

import java.util.HashMap;
import java.util.List;

/**
 * 行政机构表 服务接口层
 *
 * @author jxl
 * @date 2020-08-21 15:37:10
 */
public interface SysAdministrativeOrganService extends IService<SysAdministrativeOrgan> {
    /**
     * 查询行政机构表信息
     *
     * @param organId 行政机构表ID
     * @return 行政机构表信息
     */
    public AdministrativeOrganVO selectSysAdministrativeOrganById(Integer organId);

    /**
     * 查询行政机构表列表
     *
     * @param sysAdministrativeOrgan 行政机构表信息
     * @return 行政机构表集合
     */
    public List<SysAdministrativeOrgan> selectSysAdministrativeOrganList(SysAdministrativeOrgan sysAdministrativeOrgan);


    /**
     * 分页模糊查询行政机构表列表
     * @return 行政机构表集合
     */
    public Page selectSysAdministrativeOrganListByLike(Query query);



    /**
     * 新增行政机构表
     *
     * @param sysAdministrativeOrgan 行政机构表信息
     * @return 结果
     */
    public int insertSysAdministrativeOrgan(SysAdministrativeOrgan sysAdministrativeOrgan);

    /**
     * 修改行政机构表
     *
     * @param sysAdministrativeOrgan 行政机构表信息
     * @return 结果
     */
    public int updateSysAdministrativeOrgan(SysAdministrativeOrgan sysAdministrativeOrgan);

    /**
     * 删除行政机构表
     *
     * @param organId 行政机构表ID
     * @return 结果
     */
    public int deleteSysAdministrativeOrganById(Integer organId);

    /**
     * 批量删除行政机构表
     *
     * @param organIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysAdministrativeOrganByIds(String[] organIds);

    List<HashMap> list(Query query);

    String selectCompanyIdsByUserId(Integer userId);

    Page selectSysAdministrativeOrganListByLikeAndTenant(Query objectQuery);

    List<HashMap> inputList(String comId);
}

