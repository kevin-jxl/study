package com.ztman.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.entity.SysAdministrativeArea;
import com.ztman.common.core.util.Query;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * 行政区域表  mapper层
 *
 * @author jxl
 * @date 2020-08-21 15:36:59
 */
public interface SysAdministrativeAreaMapper extends BaseMapper<SysAdministrativeArea> {
    /**
     * 查询行政区域表信息
     *
     * @param areaId 行政区域表ID
     * @return 行政区域表信息
     */
    public SysAdministrativeArea selectSysAdministrativeAreaById(Integer areaId);

    /**
     * 查询行政区域表列表
     *
     * @param sysAdministrativeArea 行政区域表信息
     * @return 行政区域表集合
     */
    public List<SysAdministrativeArea> selectSysAdministrativeAreaList(SysAdministrativeArea sysAdministrativeArea);

    /**
     * 模糊查询行政区域表列表
     *
     * @param sysAdministrativeArea 行政区域表信息
     * @return 行政区域表集合
     */
    public List<SysAdministrativeArea> selectSysAdministrativeAreaListByLike(SysAdministrativeArea sysAdministrativeArea);


    /**
     * 分页模糊查询行政区域表列表
     *
     * @param sysAdministrativeArea 行政区域表信息
     * @return 行政区域表集合
     */
    public List<SysAdministrativeArea> selectSysAdministrativeAreaListByLike(Query query, SysAdministrativeArea sysAdministrativeArea);


    /**
     * 新增行政区域表
     *
     * @param sysAdministrativeArea 行政区域表信息
     * @return 结果
     */
    public int insertSysAdministrativeArea(SysAdministrativeArea sysAdministrativeArea);

    /**
     * 修改行政区域表
     *
     * @param sysAdministrativeArea 行政区域表信息
     * @return 结果
     */
    public int updateSysAdministrativeArea(SysAdministrativeArea sysAdministrativeArea);

    /**
     * 删除行政区域表
     *
     * @param areaId 行政区域表ID
     * @return 结果
     */
    public int deleteSysAdministrativeAreaById(Integer areaId);

    /**
     * 批量删除行政区域表
     *
     * @param areaIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysAdministrativeAreaByIds(String[] areaIds);


    public List<HashMap> list();

    SysAdministrativeArea selectSysAdministrativeAreaByCode(@Param("code") String code);
}
