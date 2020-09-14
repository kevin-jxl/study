package com.ztman.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.entity.SysServiceOrgan;
import com.ztman.admin.api.vo.SysServiceOrganVO;
import com.ztman.common.core.util.Query;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * 业务机构表  mapper层
 *
 * @author ztman code generator
 * @date 2020-08-25 19:44:47
 */
public interface SysServiceOrganMapper extends BaseMapper<SysServiceOrgan> {
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
     * 模糊查询业务机构表列表
     *
     * @param sysServiceOrgan 业务机构表信息
     * @return 业务机构表集合
     */
    public List<SysServiceOrganVO> selectSysServiceOrganListByLike(SysServiceOrgan sysServiceOrgan);


    /**
     * 分页模糊查询业务机构表列表
     *
     * @param sysServiceOrgan 业务机构表信息
     * @return 业务机构表集合
     */
    public List<SysServiceOrganVO> selectSysServiceOrganListByLike(Query query, SysServiceOrgan sysServiceOrgan);


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


    List<Integer> selectOrganIdsByUserId(Integer userId);

    List<SysServiceOrganVO> selectSysServiceOrganListByLikeOrgan(Query query, SysServiceOrgan sysServiceOrgan);

    List<HashMap<String, Object>> inputList(SysServiceOrgan sysServiceOrgan);

    SysServiceOrgan findSysServiceOrganByCode(@Param("code") String code);

    List<HashMap<String, Object>> list(@Param("organId") String organId);
}
