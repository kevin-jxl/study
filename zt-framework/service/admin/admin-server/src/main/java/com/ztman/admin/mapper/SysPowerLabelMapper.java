package com.ztman.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.entity.SysPowerLabel;
import com.ztman.admin.api.vo.SysPowerLabelVO;
import com.ztman.common.core.util.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * mapper层
 *
 * @author ztman code generator
 * @date 2019-02-22 14:43:28
 */
public interface SysPowerLabelMapper extends BaseMapper<SysPowerLabel> {
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
     * 模糊查询列表
     *
     * @param sysPowerLabel 信息
     * @return 集合
     */
    public List<SysPowerLabel> selectSysPowerLabelListByLike(SysPowerLabel sysPowerLabel);


    /**
     * 分页模糊查询列表
     *
     * @param sysPowerLabel 信息
     * @return 集合
     */
    public List<SysPowerLabelVO> selectSysPowerLabelListByLike(Query query, SysPowerLabel sysPowerLabel);


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


    List<SysPowerLabelVO> sysPowerLabelList(@Param("menuId") Object menuId, @Param("powerName") Object powerName, @Param("powerTable") Object powerTable);
}
