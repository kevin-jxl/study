package com.ztman.admin.api.feign.fallback;

import com.ztman.admin.api.dto.UserInfo;
import com.ztman.admin.api.feign.RemoteUserService;
import com.ztman.admin.api.vo.UserVO;
import com.ztman.admin.api.entity.SysPost;
import com.ztman.admin.api.entity.SysRole;
import com.ztman.admin.api.entity.SysUser;
import com.ztman.common.core.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RemoteUserServiceFallbackImpl implements RemoteUserService {
	/**
	 * 通过用户名查询用户、角色信息
	 *
	 * @param username 用户名
	 * @param from     内外标志
	 * @return R
	 */
	@Override
	public R<UserInfo> info(String username, String from) {
		log.error("feign 查询用户信息失败:{}", username);
		return null;
	}

	/**
	 * 通过社交账号查询用户、角色信息
	 *
	 * @param inStr appid@code
	 * @return
	 */
	@Override
	public R<UserInfo> social(String inStr) {
		log.error("feign 查询用户信息失败:{}", inStr);
		return null;
	}

	@Override
	public List selectUserList(String searchUser) {
		return null;
	}

	@Override
	public List<SysPost> postList() {
		return null;
	}

	@Override
	public List<SysRole> roleListAll() {
		return null;
	}

	@Override
	public List<SysUser> selectUserByRole(List<Integer> roles) {
		return null;
	}

	@Override
	public List<SysUser> selectUserByPost(String posts) {
		return null;
	}

	@Override
	public UserVO user(Integer id) {
		return null;
	}

	@Override
	public SysUser getUserByCode(String code) {
		return null;
	}
}
