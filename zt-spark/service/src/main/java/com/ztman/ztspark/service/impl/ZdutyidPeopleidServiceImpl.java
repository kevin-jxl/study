package com.ztman.ztspark.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.ztspark.entity.company.ZdutyidPeopleid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import cn.hutool.core.bean.BeanUtil;
import com.ztman.common.core.util.Query;
import com.ztman.ztspark.mapper.ZdutyidPeopleidMapper;
import com.ztman.ztspark.service.ZdutyidPeopleidService;

import java.util.List;


@Service("zdutyidPeopleidService")
public class ZdutyidPeopleidServiceImpl extends ServiceImpl<ZdutyidPeopleidMapper, ZdutyidPeopleid> implements ZdutyidPeopleidService {

    @Autowired
    private ZdutyidPeopleidMapper zdutyidPeopleidMapper;

    /**
     * 查询值班人员关联表信息
     *
     * @param dytyPeopleId 值班人员关联表ID
     * @return 值班人员关联表信息
     */
    @Override
    public ZdutyidPeopleid selectZdutyidPeopleidById(Integer dytyPeopleId)
    {
        return zdutyidPeopleidMapper.selectZdutyidPeopleidById(dytyPeopleId);
    }

    /**
     * 查询值班人员关联表列表
     *
     * @param zdutyidPeopleid 值班人员关联表信息
     * @return 值班人员关联表集合
     */
    @Override
    public List<ZdutyidPeopleid> selectZdutyidPeopleidList(ZdutyidPeopleid zdutyidPeopleid)
    {
        return zdutyidPeopleidMapper.selectZdutyidPeopleidList(zdutyidPeopleid);
    }


    /**
     * 分页模糊查询值班人员关联表列表
     * @return 值班人员关联表集合
     */
    @Override
    public Page selectZdutyidPeopleidListByLike(Query query)
    {
        ZdutyidPeopleid zdutyidPeopleid =  BeanUtil.mapToBean(query.getCondition(), ZdutyidPeopleid.class,false);
        query.setRecords(zdutyidPeopleidMapper.selectZdutyidPeopleidListByLike(query,zdutyidPeopleid));
        return query;
    }

    /**
     * 新增值班人员关联表
     *
     * @param zdutyidPeopleid 值班人员关联表信息
     * @return 结果
     */
    @Override
    public int insertZdutyidPeopleid(ZdutyidPeopleid zdutyidPeopleid)
    {
        return zdutyidPeopleidMapper.insertZdutyidPeopleid(zdutyidPeopleid);
    }

    /**
     * 修改值班人员关联表
     *
     * @param zdutyidPeopleid 值班人员关联表信息
     * @return 结果
     */
    @Override
    public int updateZdutyidPeopleid(ZdutyidPeopleid zdutyidPeopleid)
    {
        return zdutyidPeopleidMapper.updateZdutyidPeopleid(zdutyidPeopleid);
    }


    /**
     * 删除值班人员关联表
     *
     * @param dytyPeopleId 值班人员关联表ID
     * @return 结果
     */
    public int deleteZdutyidPeopleidById(Integer dytyPeopleId)
    {
        return zdutyidPeopleidMapper.deleteZdutyidPeopleidById( dytyPeopleId);
    };


    /**
     * 批量删除值班人员关联表对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteZdutyidPeopleidByIds(String[] dytyPeopleIds)
    {
        return zdutyidPeopleidMapper.deleteZdutyidPeopleidByIds( dytyPeopleIds);
    }

}
