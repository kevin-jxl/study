package com.ztman.admin.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ztman.admin.api.entity.SysPowerLabel;
import com.ztman.common.core.util.Query;

import java.util.List;
import java.util.Map;

/**
 *  服务接口层
 *
 * @author ztman code generator
 * @date 2019-02-22 14:43:28
 */
public interface SysPowerLabelService extends IService<SysPowerLabel> {
    /**
     * 查询信息
     *
     * @param id ID
     * @return 信息
     */
    public SysPowerLabel selectSysPowerLabelById(Integer id);

    /**
     * 查询列表
     *
     * @param sysPowerLabel 信息
     * @return 集合
     */
    public List<SysPowerLabel> selectSysPowerLabelList(SysPowerLabel sysPowerLabel);


    /**
     * 分页模糊查询列表
     * @return 集合
     */
    public Page selectSysPowerLabelListByLike(Query query);



    /**
     * 新增
     *
     * @param sysPowerLabel 信息
     * @return 结果
     */
    public int insertSysPowerLabel(SysPowerLabel sysPowerLabel);

    /**
     * 修改
     *
     * @param sysPowerLabel 信息
     * @return 结果
     */
    public int updateSysPowerLabel(SysPowerLabel sysPowerLabel);

    /**
     * 删除
     *
     * @param id ID
     * @return 结果
     */
    public int deleteSysPowerLabelById(Integer id);

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysPowerLabelByIds(String[] ids);

    /**
     * 列表查询不分页
     * @param params
     * @return
     */
    Map sysPowerLabelList(Map<String, Object> params);
}

