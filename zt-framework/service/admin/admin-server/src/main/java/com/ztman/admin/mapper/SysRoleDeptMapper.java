package com.ztman.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.entity.SysRoleDept;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色与部门对应关系 Mapper 接口
 * </p>
 *
 * @author ztman
 * @since 2018-01-20
 */
@Mapper
public interface SysRoleDeptMapper extends BaseMapper<SysRoleDept> {

}
