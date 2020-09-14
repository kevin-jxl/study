package com.ztman.admin.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ztman.admin.api.entity.SysSocialDetails;
import com.ztman.admin.api.entity.SysUser;
import com.ztman.admin.mapper.SysSocialDetailsMapper;
import com.ztman.admin.mapper.SysUserMapper;
import com.ztman.admin.service.SysSocialDetailsService;
import com.ztman.common.core.constant.enums.EnumLoginType;
import com.ztman.common.core.constant.SecurityConstants;
import com.ztman.common.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ztman
 * @date 2018年08月16日
 */
@Slf4j
@AllArgsConstructor
@Service("sysSocialDetailsService")
public class SysSocialDetailsServiceImpl extends ServiceImpl<SysSocialDetailsMapper, SysSocialDetails> implements SysSocialDetailsService {
	private final CacheManager cacheManager;
	private final RestTemplate restTemplate;
	private final SysUserMapper sysUserMapper;

	/**
	 * 通过客户端信息查询openId
	 *
	 * @param inStr appid @ code
	 * @return
	 */
	@Override
	public Map<String, String> findOpenId(String inStr) {
		String[] inStrs = inStr.split("@");
		return getOpenId(inStrs[0], inStrs[1]);
	}

	/**
	 * 绑定社交账号
	 *
	 * @param appId appId
	 * @param code  code
	 * @return
	 */
	@Override
	public Boolean bindSocial(String appId, String code) {
		Map<String, String> result = getOpenId(appId, code);

		SysUser condition = new SysUser();
		condition.setUsername(SecurityUtils.getUser());
		SysUser sysUser = sysUserMapper.selectOne(condition);
		sysUser.setWxOpenid(result.get("openId"));

		sysUserMapper.updateAllColumnById(sysUser);
		//更新緩存
		cacheManager.getCache("user_details").evict(result.get("openId"));
		return Boolean.TRUE;
	}

	/**
	 * 通过appid 、code 获得openID
	 *
	 * @param appId
	 * @param code
	 * @return
	 */
	private Map<String, String> getOpenId(String appId, String code) {
		SysSocialDetails condition = new SysSocialDetails();
		condition.setAppId(appId);
		SysSocialDetails socialDetails = this.baseMapper.selectOne(condition);

		String openId = null;
		//微信登录
		if (EnumLoginType.WECHAT.getType().equals(socialDetails.getType())) {
			String url = String.format(SecurityConstants.WX_AUTHORIZATION_CODE_URL
				, socialDetails.getAppId(), socialDetails.getAppSecret(), code);
			String result = restTemplate.getForObject(url, String.class);
			log.debug("微信响应报文:{}", result);

			Object obj = JSONUtil.parseObj(result).get("openid");
			if (obj == null) {
				return null;
			}
			openId = obj.toString();
		}

		Map<String, String> result = new HashMap<>(2);
		result.put("type", socialDetails.getType());
		result.put("openId", openId);
		return result;
	}

}
