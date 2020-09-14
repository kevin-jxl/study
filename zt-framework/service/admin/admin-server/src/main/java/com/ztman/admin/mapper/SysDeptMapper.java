package com.ztman.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.entity.SysDept;
import com.ztman.common.core.util.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 部门管理 Mapper 接口
 * </p>
 *
 * @author ztman
 * @since 2018-01-20
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 关联dept——relation
     *
     * @param delFlag 删除标记
     * @return 数据列表
     */
    List<SysDept> selectDeptDtoList(String delFlag);

    /**
     * 删除部门关系表数据
     *
     * @param id 部门ID
     */
    void deleteDeptRealtion(Integer id);


    /**
     * 　　* @Description: 分页查询部门列表
     * 　　* @param
     * 　　* @return
     * 　　* @author lmh
     * 　　* @date 2019/1/22 11:33
     */
    List selectDeptVoPage(Query query, @Param("deptId") Object deptId,@Param("dataSearch") Object dataSearch);

    String getChildrenOrgOfDept(Integer deptId);

    List<Integer> getParentOrgOfDept(Integer deptId);
}
