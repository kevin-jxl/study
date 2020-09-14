package com.ztman.business.inspection.mapper;

import com.ztman.business.inspection.client.entity.Equipment;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.common.core.util.Query;

/**
 * 设备表  mapper层
 *
 * @author zxx
 * @date 2020-08-29 10:35:07
 */
public interface EquipmentMapper extends BaseMapper<Equipment> {
    /**
     * 查询设备表信息
     *
     * @param id 设备表ID
     * @return 设备表信息
     */
    public Equipment selectEquipmentById(Long id);

    /**
     * 查询设备表列表
     *
     * @param equipment 设备表信息
     * @return 设备表集合
     */
    public List<Equipment> selectEquipmentList(Equipment equipment);

    /**
     * 模糊查询设备表列表
     *
     * @param equipment 设备表信息
     * @return 设备表集合
     */
    public List<Equipment> selectEquipmentListByLike(Equipment equipment);


    /**
     * 分页模糊查询设备表列表
     *
     * @param equipment 设备表信息
     * @return 设备表集合
     */
    public List<Equipment> selectEquipmentListByLike(Query query, Equipment equipment);


    /**
     * 新增设备表
     *
     * @param equipment 设备表信息
     * @return 结果
     */
    public int insertEquipment(Equipment equipment);

    /**
     * 修改设备表
     *
     * @param equipment 设备表信息
     * @return 结果
     */
    public int updateEquipment(Equipment equipment);

    /**
     * 删除设备表
     *
     * @param id 设备表ID
     * @return 结果
     */
    public int deleteEquipmentById(Long id);

    /**
     * 批量删除设备表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteEquipmentByIds(String[] ids);



}
