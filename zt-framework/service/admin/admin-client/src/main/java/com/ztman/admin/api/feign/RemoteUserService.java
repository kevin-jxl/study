package com.ztman.admin.api.feign;

import com.ztman.admin.api.vo.UserVO;
import com.ztman.admin.api.dto.UserInfo;
import com.ztman.admin.api.entity.SysPost;
import com.ztman.admin.api.entity.SysRole;
import com.ztman.admin.api.entity.SysUser;
import com.ztman.common.core.constant.ServiceNameConstant;
import com.ztman.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = ServiceNameConstant.UMPS_SERVICE)
public interface RemoteUserService {
    /**
     * 通过用户名查询用户、角色信息
     *
     * @param username 用户名
     * @param from     调用标志
     * @return R
     */
    @GetMapping("/user/info/{username}")
    R<UserInfo> info(@PathVariable("username") String username
            , @RequestHeader("from") String from);


    /**
     * 通过社交账号查询用户、角色信息
     *
     * @param inStr appid@code
     * @return
     */
    @GetMapping("/social/info/{inStr}")
    R<UserInfo> social(@PathVariable("inStr") String inStr);

    /**
     * 获取用户列表
     *
     * @param
     * @return
     */
    @PostMapping("/user/selectUserList")
    List selectUserList(@RequestParam("searchUser") String searchUser);

    /**
     * 获取岗位列表
     *
     * @param
     * @return
     */
    @GetMapping("/syspost/postList")
    List<SysPost> postList();

    /**
     * 获取角色列表
     *
     * @param
     * @return
     */
    @GetMapping("/role/roleListAll")
    List<SysRole> roleListAll();

    /**
     * 根据角色获取用户列表
     *
     * @param
     * @return
     */
    @PostMapping("/role/selectUserByRole")
    List<SysUser> selectUserByRole(@RequestParam("roles") List<Integer> roles);
    /**
     * 根据岗位获取用户列表
     *
     * @param
     * @return
     */
    @PostMapping("/syspost/postList")
    List<SysUser> selectUserByPost(@RequestParam("posts") String posts);
    /**
     * 根据用户ID获取用户
     *
     * @param
     * @return
     */
    @GetMapping("/user")
    UserVO user(@RequestParam("id") Integer id);
    /**
     * 根据用户ID获取用户
     *
     * @param
     * @return
     */
    @GetMapping("/user/getUserByCode")
    SysUser getUserByCode(@RequestParam("code") String code);
}
