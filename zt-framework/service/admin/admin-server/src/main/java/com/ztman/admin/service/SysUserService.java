package com.ztman.admin.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ztman.admin.api.dto.UserDTO;
import com.ztman.admin.api.dto.UserInfo;
import com.ztman.admin.api.entity.SysUser;
import com.ztman.admin.api.entity.SysUserPost;
import com.ztman.admin.api.vo.RoleVO;
import com.ztman.admin.api.vo.UserVO;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import com.ztman.tools.file.AttackmentDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ztman
 * @date 2017/10/31
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 查询用户信息
     *
     * @param type     类型
     * @param username 用户名
     * @return userInfo
     */
    UserInfo findUserInfo(String type, String username);

    /**
     * 分页查询用户信息（含有角色信息）
     *
     * @param query 查询条件
     * @return
     */
    Page selectWithRolePage(Query query);

    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return boolean
     */
    Boolean deleteUserById(SysUser sysUser);

    /**
     * 更新当前用户基本信息
     *
     * @param userDto  用户信息
     * @param username 用户名
     * @return Boolean
     */
    R<Boolean> updateUserInfo(UserDTO userDto, String username);

    /**
     * 更新指定用户信息
     *
     * @param userDto  用户信息
     * @param username 用户信息
     * @return
     */
    Boolean updateUser(UserDTO userDto, String username);

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    UserVO selectUserVoById(Integer id);

    void insertUserPost(SysUserPost userPost);

    R<Boolean> sendAuthCode(String params);

    R<Boolean> contrastAuthCode(String authCode, String encrypted);

    R<Boolean> updatePassword(UserDTO userDto);

    void userPosDummy(Integer userId, String posDummys);

    AttackmentDto uploadSignName(MultipartFile[] files);

    List<RoleVO> selectUserRoleByUserId(int id);

    List<SysUser> selectAdminList(EntityWrapper<SysUser> sysUserEntityWrapper);

    SysUser findSysUser(SysUser sysUser);

    R findUserInfoR(String type, String username);

    Boolean updateUserDetails(UserDTO userDto, String username);

    SysUser selectSysUserByUserName(String username);
}
