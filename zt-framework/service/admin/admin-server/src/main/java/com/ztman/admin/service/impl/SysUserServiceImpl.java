package com.ztman.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ztman.admin.api.dto.UserDTO;
import com.ztman.admin.api.dto.UserInfo;
import com.ztman.admin.api.entity.SysRole;
import com.ztman.admin.api.entity.SysUser;
import com.ztman.admin.api.entity.SysUserPost;
import com.ztman.admin.api.entity.SysUserRole;
import com.ztman.admin.api.vo.MenuVO;
import com.ztman.admin.api.vo.RoleVO;
import com.ztman.admin.api.vo.UserVO;
import com.ztman.admin.mapper.SysDeptMapper;
import com.ztman.admin.mapper.SysUserMapper;
import com.ztman.admin.service.SysMenuService;
import com.ztman.admin.service.SysRoleService;
import com.ztman.admin.service.SysUserRoleService;
import com.ztman.admin.service.SysUserService;
import com.ztman.common.core.constant.CommonConstant;
import com.ztman.common.core.constant.enums.EnumLoginType;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import com.ztman.tools.file.AttackmentDto;
import com.ztman.tools.file.FastDfsUtil;
import com.ztman.tools.sms.SmsSendUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author ztman
 * @date 2017/10/31
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    private final SysMenuService sysMenuService;
    private final SysUserMapper sysUserMapper;
    private final SysDeptMapper sysDeptMapper;
    private final SysRoleService sysRoleService;
    private final SysUserRoleService sysUserRoleService;
//    @Autowired
//    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    @Autowired
    private  ObjectMapper objectMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public R findUserInfoR(String type, String username ) {
        SysUser condition = new SysUser();
        if (EnumLoginType.PWD.getType().equals(type)) {
            condition.setUsername(username);
        } else if (EnumLoginType.WECHAT.getType().equals(type)) {
            condition.setWxOpenid(username);
        } else {
            condition.setQqOpenid(username);
        }
        SysUser sysUser = this.selectOne(new EntityWrapper<>(condition));
        if (sysUser == null) {
            return null;
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setSysUser(sysUser);
        //设置角色列表
        List<SysRole> roleList = sysRoleService.findRolesByUserId(sysUser.getUserId());
        List<String> roleCodes = new ArrayList<>();
        if (CollUtil.isNotEmpty(roleList)) {
            roleList.forEach(sysRole -> roleCodes.add(sysRole.getRoleCode()));
        }
        userInfo.setRoles(ArrayUtil.toArray(roleCodes, String.class));
        if(!CommonConstant.STATUS_NORMAL.equals(sysUser.getTenantFlag())
               && !roleCodes.contains(CommonConstant.ROLE_ADMIN)){
            return new R(886,Boolean.FALSE,"权限不足,禁止访问");
        }
        //设置权限列表（menu.permission）
        Set<MenuVO> menuVoSet = new HashSet<>();
        for (String role : roleCodes) {
            List<MenuVO> menuVos = sysMenuService.findMenuByRoleCode(role,"");
            menuVoSet.addAll(menuVos);
        }
        Set<String> permissions = new HashSet<>();
        for (MenuVO menuVo : menuVoSet) {
            if (StringUtils.isNotEmpty(menuVo.getPermission())) {
                String permission = menuVo.getPermission();
                permissions.add(permission);
            }
        }
        userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));
        return new R(userInfo);
    }

    /**
     * 通过用户名查用户的全部信息
     *
     * @param username 用户名
     * @return
     */

    @Override
    @Cacheable(value = "user_details", key = "#username")
    public UserInfo findUserInfo(String type, String username) {
        SysUser condition = new SysUser();
        if (EnumLoginType.PWD.getType().equals(type)) {
            condition.setUsername(username);
        } else if (EnumLoginType.WECHAT.getType().equals(type)) {
            condition.setWxOpenid(username);
        } else {
            condition.setQqOpenid(username);
        }
        SysUser sysUser = this.selectOne(new EntityWrapper<>(condition));
        if (sysUser == null) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setSysUser(sysUser);
        //设置角色列表
        List<SysRole> roleList = sysRoleService.findRolesByUserId(sysUser.getUserId());
        List<String> roleCodes = new ArrayList<>();
        if (CollUtil.isNotEmpty(roleList)) {
            roleList.forEach(sysRole -> roleCodes.add(sysRole.getRoleCode()));
        }
        userInfo.setRoles(ArrayUtil.toArray(roleCodes, String.class));

        //设置权限列表（menu.permission）
        Set<MenuVO> menuVoSet = new HashSet<>();
        for (String role : roleCodes) {
            List<MenuVO> menuVos = sysMenuService.findMenuByRoleCode(role,"");
            menuVoSet.addAll(menuVos);
        }
        Set<String> permissions = new HashSet<>();
        for (MenuVO menuVo : menuVoSet) {
            if (StringUtils.isNotEmpty(menuVo.getPermission())) {
                String permission = menuVo.getPermission();
                permissions.add(permission);
            }
        }
        userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));
        return userInfo;
    }

    @Override
    public Page selectWithRolePage(Query query) {
        List list = new ArrayList<>();
        Object username = query.getCondition().get("username");
        Integer userId = (Integer) query.getCondition().get("userId");
        String roleCode = (String) query.getCondition().get("roleCode");
        Object userSearch = query.getCondition().get("userSearch");
        Object companyId = query.getCondition().get("companyId");
        Object organId = query.getCondition().get("organId");
        //根据子ID查询出所有的菜单
        list = sysUserMapper.selectUserVoPage(query, username, userId, userSearch,roleCode,companyId,organId);
        query.setRecords(list);
        return query;
    }

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    public UserVO selectUserVoById(Integer id) {
        List<Integer> deptList = new ArrayList<>();
        UserVO userVO = sysUserMapper.selectUserVoById(id);
        if (userVO.getDeptId() != null) {
            deptList = sysDeptMapper.getParentOrgOfDept(userVO.getDeptId());
        }
        userVO.setDeptList(deptList);
        return userVO;
    }

    @Override
    public void insertUserPost(SysUserPost userPost) {
        sysUserMapper.insertUserPost(userPost);
    }


    /**
     * 删除用户
     *
     * @param sysUser 用户
     * @return Boolean
     */
    @Override
    @CacheEvict(value = "user_details", key = "#sysUser.username")
    public Boolean deleteUserById(SysUser sysUser) {
        sysUserRoleService.deleteByUserId(sysUser.getUserId());
        this.deleteById(sysUser.getUserId());
        return Boolean.TRUE;
    }

    @Override
    @CacheEvict(value = "user_details", key = "#username")
    public R<Boolean> updateUserInfo(UserDTO userDto, String username) {
        UserVO userVO = sysUserMapper.selectSimpleUserVoByUsername(username);
        SysUser sysUser = new SysUser();
        if (StrUtil.isNotBlank(userDto.getPassword())
                && StrUtil.isNotBlank(userDto.getNewpassword1())) {
            //if (ENCODER.matches(userDto.getPassword(), userVO.getPassword())) {
            //sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
            if (userVO.getPassword().toLowerCase().equals(SecureUtil.md5((String) userDto.getPassword()))) {
                sysUser.setPassword(SecureUtil.md5(userDto.getNewpassword1()));
            } else {
                log.warn("原密码错误，修改密码失败:{}", username);
                return new R<>(Boolean.FALSE, "原密码错误，修改失败");
            }
        }
        sysUser.setPhone(userDto.getPhone());
        sysUser.setUserId(userVO.getUserId());
        sysUser.setAvatar(userDto.getAvatar());
        return new R<>(this.updateById(sysUser));
    }


    @Override
    public R<Boolean> sendAuthCode(String params) {
        Boolean b = Boolean.FALSE;
        String msg = "请输入正确的手机号或邮箱";
        String email = null;
        String phone = null;

        //验证账号是否存在
        if (params != null && !"".equals(params)) {
            //判断是邮箱还是手机
            if (params.indexOf("@") != -1) {
                //邮箱
                email = params;
            } else {
                //手机
                phone = params;
            }
            //根据认证账号查询是否有效
            SysUser user = sysUserMapper.selectAuthCode(email, phone);
            if (user != null) {
                //随机生成6位数密码
                String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);

                //将验证码存入redis
                RedisSerializer<String> stringSerializer = new StringRedisSerializer();
                redisTemplate.setValueSerializer(stringSerializer);
                redisTemplate.opsForValue().set(params, verifyCode, 5, TimeUnit.MINUTES);

                //发送验证（手机或者邮箱）
                if (params.indexOf("@") != -1) {
                    //邮箱

                } else {
                    //手机
                    b = SmsSendUtil.SmsSend(params, "您的验证码是：" + verifyCode + "(验证码有效时间5分钟)");
                }
                msg = "验证码发送成功";
            }
        }
        return new R<>(b, msg);
    }

    @Override
    public R<Boolean> contrastAuthCode(String authCode, String encrypted) {
        Boolean b = Boolean.FALSE;
        String msg = "验证码不正确";
        //将验证码存入redis
        Object redisObj = redisTemplate.opsForValue().get(encrypted);
        if (redisObj != null) {
            String redisCode = redisObj.toString();
            if (redisCode.equals(authCode)) {
                b = Boolean.TRUE;
                msg = "验证成功";
            }
        }
        return new R<>(b, msg);
    }

    @Override
    public R<Boolean> updatePassword(UserDTO userDto) {
        Boolean b = Boolean.FALSE;
        String msg = "修改失败";

        String email = null;
        String phone = null;
        SysUser sysUser = new SysUser();

        //根据手机号或者邮箱查询出账号详情
        String encrypted = userDto.getEncrypted();
        if (encrypted != null && !"".equals(encrypted)) {
            if (encrypted.indexOf("@") != -1) {
                email = encrypted;
            } else {
                phone = encrypted;
            }

            //根据认证账号查询是否有效
            SysUser user = sysUserMapper.selectAuthCode(email, phone);
            if (user != null) {
                sysUser.setUserId(user.getUserId());
                sysUser.setPassword(SecureUtil.md5(userDto.getNewpassword1()));
                this.updateById(sysUser);
                b = Boolean.TRUE;
                msg = "修改成功";
            }
        }
        return new R<>(b, msg);
    }

    @Override
    public void userPosDummy(Integer userId, String posDummys) {
        sysUserMapper.deleteUserPosDummy(userId);
        if (userId != null && posDummys != null && !"".equals(posDummys)) {
            List list = new ArrayList();
            String[] split = posDummys.split(",");
            for (String s : split) {
                list.add(Integer.valueOf(s));
            }
            sysUserMapper.insertUserPosDummy(userId, list);
        }
    }

    @Override
    public AttackmentDto uploadSignName(MultipartFile[] files) {
        AttackmentDto dto = new AttackmentDto();
        dto.setCode(500);
        dto.setMsg("电子签名上传异常");
        List<AttackmentDto> dtos = FastDfsUtil.webUpload(files, "");
        if (dtos != null && dtos.size() > 0) {
            dto = dtos.get(0);
        }
        return dto;
    }

    @Override
    public List<RoleVO> selectUserRoleByUserId(int id) {

        return sysUserMapper.selectUserRoleByUserId(id);
    }

    @Override
    public List<SysUser> selectAdminList(EntityWrapper<SysUser> sysUserEntityWrapper) {
        SysUser entity = sysUserEntityWrapper.getEntity();
        return sysUserMapper.selectAdminList(entity);
    }

    @Override
    public SysUser findSysUser(SysUser sysUser) {
        return sysUserMapper.findSysUser(sysUser);
    }

    @Override
    @CacheEvict(value = "user_details", key = "#username")
    @Transactional
    public Boolean updateUser(UserDTO userDto, String username) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setUpdateTime(LocalDateTime.now());
        this.updateById(sysUser);

        SysUserRole condition = new SysUserRole();
        condition.setUserId(userDto.getUserId());
        sysUserRoleService.delete(new EntityWrapper<>(condition));
        Integer roleId = userDto.getRoleId();
        if (roleId != null) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            userRole.insert();
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateUserDetails(UserDTO userDto, String username) {

        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setUpdateTime(LocalDateTime.now());
        boolean boo = sysUserMapper.updateUserDetails(sysUser) > 0;

        SysUserRole condition = new SysUserRole();
        condition.setUserId(userDto.getUserId());
        sysUserRoleService.delete(new EntityWrapper<>(condition));
        Integer roleId = userDto.getRoleId();
        if (roleId != null) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(sysUser.getUserId());
            userRole.setRoleId(roleId);
            userRole.insert();
        }
        return boo;
    }

    @Override
    public SysUser selectSysUserByUserName(String username) {
        return sysUserMapper.selectSysUserByUserName(username);
    }
}
