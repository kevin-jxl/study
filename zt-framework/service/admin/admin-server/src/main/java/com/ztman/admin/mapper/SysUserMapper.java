package com.ztman.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ztman.admin.api.entity.SysUser;
import com.ztman.admin.api.entity.SysUserPost;
import com.ztman.admin.api.vo.RoleVO;
import com.ztman.admin.api.vo.UserVO;
import com.ztman.common.core.util.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author ztman
 * @since 2017-10-29
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 通过用户名查询用户信息（含有角色信息）
     *
     * @param username 用户名
     * @return userVo
     */
    UserVO selectUserVoByUsername(String username);

    /**
     * 通过用户名查询用户信息（含有角色信息）
     *
     * @param username 用户名
     * @return userVo
     */
    UserVO selectSimpleUserVoByUsername(String username);

    /**
     * 分页查询用户信息（含角色）
     *
     * @param query    查询条件
     * @param username 用户名
     * @return list
     */
    List selectUserVoPage(Query query, @Param("username") Object username, @Param("userId") Integer userId, @Param("userSearch") Object userSearch, @Param("roleCode") String roleCode,@Param("companyId")Object companyId,@Param("organId") Object organId);

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return userVo
     */
    UserVO selectUserVoById(Integer id);

    void insertUserPost(SysUserPost userPost);

    void deletePostByUser(Integer userId);

    SysUser selectAuthCode(@Param("email") String email, @Param("phone") String phone);

    void insertUserPosDummy(@Param("userId") Integer userId, @Param("list") List posDummys);

    void deleteUserPosDummy(@Param("userId") Integer userId);

    List selectUserByPost(List<Integer> list);

    List selectUserByRole(List<Integer> list);

    List<RoleVO> selectUserRoleByUserId(@Param("userId") int id);

    List<Integer> selectTenantList(@Param("userId") Integer userId);

    List<SysUser> selectAdminList(SysUser entity);

    SysUser findSysUser(SysUser sysUser);

    int updateUserDetails(SysUser sysUser);

    SysUser selectSysUserByUserName(String username);
}
