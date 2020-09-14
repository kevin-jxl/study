package com.ztman.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.entity.SysAdministrativeOrgan;
import com.ztman.admin.api.vo.AdministrativeOrganVO;
import com.ztman.common.core.util.Query;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * 行政机构表  mapper层
 *
 * @author jxl
 * @date 2020-08-21 15:37:10
 */
public interface SysAdministrativeOrganMapper extends BaseMapper<SysAdministrativeOrgan> {
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
     * 模糊查询行政机构表列表
     *
     * @param sysAdministrativeOrgan 行政机构表信息
     * @return 行政机构表集合
     */
    public List<AdministrativeOrganVO> selectSysAdministrativeOrganListByLike(SysAdministrativeOrgan sysAdministrativeOrgan);


    /**
     * 分页模糊查询行政机构表列表
     *
     * @param sysAdministrativeOrgan 行政机构表信息
     * @return 行政机构表集合
     */
    public List<AdministrativeOrganVO> selectSysAdministrativeOrganListByLike(Query query, SysAdministrativeOrgan sysAdministrativeOrgan);


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


    List<HashMap> list(SysAdministrativeOrgan sysAdministrativeOrgan);

    List<Integer> selectCompanyIdsByUserId(Integer userId);

    List<AdministrativeOrganVO> selectSysAdministrativeOrganListByLikeAndTenant(Query query, SysAdministrativeOrgan sysAdministrativeOrgan);

    SysAdministrativeOrgan findSysAdministrativeOrganByCode(@Param("code") String code);

    List<HashMap> inputList(@Param("comId") String comId);
}
