package com.ztman.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.entity.SysRole;
import com.ztman.common.core.util.Query;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ztman
 * @since 2017-10-29
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

	/**
	 * 查询角色列表含有部门信息
	 *
	 * @param query     查询对象
	 * @param condition 条件
	 * @return List
	 */
	List<Object> selectRolePage(Query<Object> query, Map<String, Object> condition);

	/**
	 * 通过角色名称查询角色列表
	 * @param query     查询对象
	 * @param condition 条件
	 * @return
	 */
	List<Object> selectRolePageByRoleName(Query<Object> query, Map<String, Object> condition);

	/**
	 * 通过用户ID，查询角色信息
	 *
	 * @param userId
	 * @return
	 */
	List<SysRole> findRolesByUserId(Integer userId);

	List<Integer> selectListRoleId(@Param("roleId") Integer roleId,@Param("roleCode") String roleCode,@Param("ptName")String ptName);

    List<Object> selectRolePageAllByRoleName(Query<Object> query, Map<String, Object> condition);

    void deleteDefaultCreateRole(SysRole sss);
}
