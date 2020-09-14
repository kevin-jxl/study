package com.ztman.admin.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ztman.admin.api.dto.UserInfo;
import com.ztman.admin.api.entity.SysSocialDetails;
import com.ztman.admin.service.SysSocialDetailsService;
import com.ztman.admin.service.SysUserService;
import com.ztman.common.core.util.Query;
import com.ztman.common.core.util.R;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 系统社交登录账号表
 */
@RestController
@RequestMapping("/social")
@AllArgsConstructor
public class SysSocialDetailsController {
	private final SysUserService sysUserService;
	private final SysSocialDetailsService sysSocialDetailsService;


	/**
	 * 列表
	 *
	 * @param params
	 * @return
	 */
	@GetMapping("/page")
	public Page page(@RequestParam Map<String, Object> params) {
		return sysSocialDetailsService.selectPage(new Query<>(params), new EntityWrapper<>());
	}


	/**
	 * 信息
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	public R info(@PathVariable("id") Integer id) {
		SysSocialDetails sysSocialDetails = sysSocialDetailsService.selectById(id);
		return new R<>(sysSocialDetails);
	}

	/**
	 * 保存
	 *
	 * @param sysSocialDetails
	 * @return R
	 */
	@PostMapping
	public R save(@RequestBody SysSocialDetails sysSocialDetails) {
		sysSocialDetailsService.insert(sysSocialDetails);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 修改
	 *
	 * @param sysSocialDetails
	 * @return R
	 */
	@PutMapping
	public R update(@RequestBody SysSocialDetails sysSocialDetails) {
		sysSocialDetailsService.updateById(sysSocialDetails);
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 删除
	 *
	 * @param ids
	 * @return R
	 */
	@DeleteMapping
	public R delete(@RequestBody Integer[] ids) {
		sysSocialDetailsService.deleteBatchIds(Arrays.asList(ids));
		return new R<>(Boolean.TRUE);
	}

	/**
	 * 通过社交账号查询用户、角色信息
	 *
	 * @param inStr appid@code
	 * @return
	 */
	@GetMapping("/info/{inStr}")
	public R<UserInfo> social(@PathVariable String inStr) {
		Map<String,String> result = sysSocialDetailsService.findOpenId(inStr);
		return new R<>(sysUserService.findUserInfo(result.get("type"), result.get("openId")));
	}

	/**
	 * 绑定社交账号
	 *
	 * @param state 类型
	 * @param code  code
	 * @return
	 */
	@PostMapping("/bind")
	public R<Boolean> bind(String state, String code) {
		return new R<>(sysSocialDetailsService.bindSocial(state, code));
	}
}
