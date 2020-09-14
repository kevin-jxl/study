package com.ztman.ztspark.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.common.core.util.Query;
import com.ztman.ztspark.entity.company.ZdutyidPeopleid;

import java.util.List;

/**
 * 值班人员关联表  mapper层
 *
 * @author spark code generator
 * @date 2020-09-08 19:07:33
 */
public interface ZdutyidPeopleidMapper extends BaseMapper<ZdutyidPeopleid> {
    /**
     * 查询值班人员关联表信息
     *
     * @param dytyPeopleId 值班人员关联表ID
     * @return 值班人员关联表信息
     */
    public ZdutyidPeopleid selectZdutyidPeopleidById(Integer dytyPeopleId);

    /**
     * 查询值班人员关联表列表
     *
     * @param zdutyidPeopleid 值班人员关联表信息
     * @return 值班人员关联表集合
     */
    public List<ZdutyidPeopleid> selectZdutyidPeopleidList(ZdutyidPeopleid zdutyidPeopleid);

    /**
     * 模糊查询值班人员关联表列表
     *
     * @param zdutyidPeopleid 值班人员关联表信息
     * @return 值班人员关联表集合
     */
    public List<ZdutyidPeopleid> selectZdutyidPeopleidListByLike(ZdutyidPeopleid zdutyidPeopleid);


    /**
     * 分页模糊查询值班人员关联表列表
     *
     * @param zdutyidPeopleid 值班人员关联表信息
     * @return 值班人员关联表集合
     */
    public List<ZdutyidPeopleid> selectZdutyidPeopleidListByLike(Query query, ZdutyidPeopleid zdutyidPeopleid);


    /**
     * 新增值班人员关联表
     *
     * @param zdutyidPeopleid 值班人员关联表信息
     * @return 结果
     */
    public int insertZdutyidPeopleid(ZdutyidPeopleid zdutyidPeopleid);

    /**
     * 修改值班人员关联表
     *
     * @param zdutyidPeopleid 值班人员关联表信息
     * @return 结果
     */
    public int updateZdutyidPeopleid(ZdutyidPeopleid zdutyidPeopleid);

    /**
     * 删除值班人员关联表
     *
     * @param dytyPeopleId 值班人员关联表ID
     * @return 结果
     */
    public int deleteZdutyidPeopleidById(Integer dytyPeopleId);

    /**
     * 批量删除值班人员关联表
     *
     * @param dytyPeopleIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteZdutyidPeopleidByIds(String[] dytyPeopleIds);



}
