package com.ztman.admin.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.dto.UserDTO;
import com.ztman.admin.api.dto.UserInfo;
import com.ztman.admin.api.entity.SysUser;
import com.ztman.admin.api.entity.SysUserRole;
import com.ztman.admin.api.vo.SysUserVO;
import com.ztman.admin.api.vo.UserVO;
import com.ztman.admin.service.SysUserService;
import com.ztman.common.core.constant.CommonConstant;
import com.ztman.common.core.constant.SecurityConstants;
import com.ztman.common.core.constant.enums.EnumLoginType;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import com.ztman.common.log.annotation.SysLog;
import com.ztman.common.security.util.SecurityUtils;
import com.ztman.tools.file.AttackmentDto;
import com.ztman.tools.other.PinYinUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    @Autowired
    private SysUserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 获取当前用户信息（角色、权限）
     * 并且异步初始化用户部门信息
     *
     * @param from     请求标志，该接口会被 auth、 前端调用
     * @param username 用户名
     * @return 用户名
     */
    @GetMapping(value = {"/info", "/info/{username}"})
    public R<UserInfo> user(@PathVariable(required = false) String username,
                            @RequestHeader(required = false) String from) {
        // 查询用户不为空时判断是不是内部请求
        if (StrUtil.isNotBlank(username) && !StrUtil.equals(SecurityConstants.FROM_IN, from)) {
            return new R<>(null, "error");
        }
        //为空时查询当前用户
        if (StrUtil.isBlank(username)) {
            username = SecurityUtils.getUser();
        }
        UserInfo userInfo = userService.findUserInfo(EnumLoginType.PWD.getType(), username);
        return new R<>(userInfo);
    }

    @GetMapping(value = {"/login", "/login/{username}"})
    public R<UserInfo> login(@PathVariable(required = false) String username,
                             @RequestHeader(required = false) String from) {
        // 查询用户不为空时判断是不是内部请求
        if (StrUtil.isNotBlank(username) && !StrUtil.equals(SecurityConstants.FROM_IN, from)) {
            return new R<>(null, "error");
        }
        //为空时查询当前用户
        if (StrUtil.isBlank(username)) {
            username = SecurityUtils.getUser();
        }
        R r = userService.findUserInfoR(EnumLoginType.PWD.getType(), username);
        return r;
    }

    /**
     * 通过ID查询当前用户信息
     *
     * @param id ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public UserVO user(@PathVariable Integer id) {
        return userService.selectUserVoById(id);
    }

    /**
     * 删除用户信息
     *
     * @param id ID
     * @return R
     */
    @SysLog("删除用户信息")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('sys_user_del')")
    @ApiOperation(value = "删除用户", notes = "根据ID删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "int", paramType = "path", example = "1000")
    public R<Boolean> userDel(@PathVariable Integer id) {
        SysUser sysUser = userService.selectById(id);
        //删除用户管理关联信息
        if(sysUser.getUserId()==1){
            return new R<>(false,"此账号不允许删除");
        }
        return new R<>(userService.deleteUserById(sysUser));
    }

    /**
     * 添加用户
     *
     * @param userDto 用户信息
     * @return success/false
     */
    @SysLog("添加用户")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_user_add')")
    public R<Boolean> user(@RequestBody UserDTO userDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setDelFlag(CommonConstant.STATUS_NORMAL);
        SysUser user = userService.findSysUser(sysUser);
        if (user != null && (!"".equals(user.getUsername()) || !"".equals(user.getPhone()))) {
            return new R<>(Boolean.FALSE,"已存在相同数据，无法添加");
        }
        sysUser.setCreateBy(String.valueOf(SecurityUtils.getUserInfo().getId()));
        sysUser.setPassword(SecureUtil.md5((String) userDto.getPassword()));
        sysUser.setTenantFlag("0");

        boolean flag = true;
        String username = PinYinUtil.toPinyin(sysUser.getUserRealname());
        do {
            SysUser olduser = userService.selectSysUserByUserName(username);
            if (null == olduser) {
                flag = false;
            } else {
                username += PinYinUtil.randomChar();
            }
        }while (flag);

        sysUser.setUsername(username);

        userService.insert(sysUser);
        Integer roleId = userDto.getRoleId();
        if (roleId != null) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            userRole.insert();
        }
        return new R<>(Boolean.TRUE);
    }

    /**
     * 更新用户信息
     *
     * @param userDto 用户信息
     * @return R
     */
    @SysLog("更新用户信息")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('sys_user_edit')")
    public R<Boolean> userUpdate(@RequestBody UserDTO userDto) {
        userDto.setCreateBy(String.valueOf(SecurityUtils.getUserInfo().getId()));
        SysUser user = userService.selectById(userDto.getUserId());
        return new R(userService.updateUserDetails(userDto, user.getUsername()));
    }

    /**
     * 分页查询用户
     *
     * @param params 参数集
     * @return 租户集合
     */
    @GetMapping("/userPage")
    public Page userPage(@RequestParam Map<String, Object> params) {

     params.put("userId", SecurityUtils.getUserInfo().getId());
     params.put("roleCode", CommonConstant.ROLE_ADMIN);
     return userService.selectWithRolePage(new Query(params));
    }

    /**
     * 查询用户集合
     *
     * @param params 参数集
     * @return 租户集合
     */
    @PostMapping("/userList")
    public List<SysUser> userList(@RequestBody SysUser params) {
        List<SysUser> sysUsers = userService.selectList(new EntityWrapper<>(params));
        return sysUsers;
    }

    /**
     * 查询用户集合
     *
     * @param params 参数集
     * @return 租户集合
     */
    @PostMapping("/tenantUserList")
    public List<SysUser> userList(@RequestBody SysUserVO params) {
        SysUser sysUser = new SysUser();
        if("service".equals(params.getType())){
            params.setServiceOrganId(params.getTenantId());
        }
        if("organ".equals(params.getType())){
            params.setOrganId(params.getTenantId());
        }
        params.setType(null);
        params.setTenantId(null);
        BeanUtils.copyProperties(params,sysUser);
        List<SysUser> sysUsers = userService.selectList(new EntityWrapper<>(sysUser));
        return sysUsers;
    }
    /**
     * 管理员集合
     *
     * @param params 参数集
     * @return 租户集合
     */
    @PostMapping("/adminList")
    public List<SysUser> adminList(@RequestBody SysUser params) {
        List<SysUser> sysUsers = userService.selectAdminList(new EntityWrapper<>(params));
        return sysUsers;
    }


    /**
     * 修改个人信息
     *
     * @param userDto userDto
     * @return success/false
     */
    @PutMapping("/editInfo")
    public R<Boolean> editInfo(@RequestBody UserDTO userDto) {
        return userService.updateUserInfo(userDto, SecurityUtils.getUser());
    }

    /**
     * 设置用户虚拟岗位
     *
     * @param userId
     * @param posDummys
     * @return
     */
    @GetMapping("/userPosDummy")
    public R userPosDummy(@RequestParam() Integer userId,
                          @RequestParam() String posDummys) {

        userService.userPosDummy(userId, posDummys);
        return new R<>(Boolean.TRUE);
    }

    /**
     * 查询用户列表
     *
     * @param searchUser
     * @return
     */
    @PostMapping(value = {"/selectUserList"})
    public List selectUserList(@RequestParam("searchUser") String searchUser) {
        SysUser user = new SysUser();
        user.setUserFlag("1");
        user.setDelFlag("1");
        EntityWrapper<SysUser> wrapper = new EntityWrapper<SysUser>(user);
        if (searchUser != null && !"".equals(searchUser)) {
            wrapper.and().like("user_realname", searchUser, SqlLike.DEFAULT);
        }
        List<SysUser> users = userService.selectList(wrapper);
        if (users != null && users.size() > 0) {
            for (SysUser s : users) {
                s.setPassword(null);
            }
        }
        return users;
    }


    /**
     * 根据code查询用户
     *
     * @param code
     * @return
     */
    @GetMapping("/getUserByCode")
    public SysUser getUserByCode(@RequestParam("code") String code) {
        SysUser query = new SysUser();
        query.setEmpno(code);
        EntityWrapper<SysUser> wrapper = new EntityWrapper<SysUser>(query);
        return userService.selectOne(wrapper);
    }


    /**
     * 上传个人电子签名
     *
     * @param files
     * @return
     */
    @PostMapping("/uploadSignName")
    public AttackmentDto uploadSignName(@RequestParam("file") MultipartFile[] files) {
        AttackmentDto dto = new AttackmentDto();
        try {
            if (files != null) {
                dto = userService.uploadSignName(files);
            } else {
                dto.setCode(500);
                dto.setMsg("电子签名上传异常");
            }
            return dto;
        } catch (Exception e) {
            dto.setCode(500);
            dto.setMsg("电子签名上传异常");
            return dto;
        }
    }
}
