package com.ztman.business.inspection.mapper;

import com.ztman.business.inspection.client.dto.PoininspectionJobDTO;
import com.ztman.business.inspection.client.entity.CasualPoininspection;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.common.core.util.Query;

/**
 * 随机点检  mapper层
 *
 * @author zxx
 * @date 2020-08-30 01:56:43
 */
public interface CasualPoininspectionMapper extends BaseMapper<CasualPoininspection> {
    /**
     * 查询随机点检信息
     *
     * @param id 随机点检ID
     * @return 随机点检信息
     */
    public CasualPoininspection selectCasualPoininspectionById(Integer id);

    /**
     * 查询随机点检列表
     *
     * @param casualPoininspection 随机点检信息
     * @return 随机点检集合
     */
    public List<CasualPoininspection> selectCasualPoininspectionList(CasualPoininspection casualPoininspection);

    /**
     * 模糊查询随机点检列表
     *
     * @param casualPoininspection 随机点检信息
     * @return 随机点检集合
     */
    public List<CasualPoininspection> selectCasualPoininspectionListByLike(CasualPoininspection casualPoininspection);


    /**
     * 分页模糊查询随机点检列表
     *
     * @param casualPoininspection 随机点检信息
     * @return 随机点检集合
     */
    public List<CasualPoininspection> selectCasualPoininspectionListByLike(Query query, CasualPoininspection casualPoininspection);


    /**
     * 新增随机点检
     *
     * @param casualPoininspection 随机点检信息
     * @return 结果
     */
    public int insertCasualPoininspection(CasualPoininspection casualPoininspection);

    /**
     * 修改随机点检
     *
     * @param casualPoininspection 随机点检信息
     * @return 结果
     */
    public int updateCasualPoininspection(CasualPoininspection casualPoininspection);

    /**
     * 删除随机点检
     *
     * @param id 随机点检ID
     * @return 结果
     */
    public int deleteCasualPoininspectionById(Integer id);

    /**
     * 批量删除随机点检
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCasualPoininspectionByIds(String[] ids);



}
