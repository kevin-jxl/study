package com.ztman.common.security.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author joy
 * @date 2019/4/8
 * 用户信息
 */
public class SparkUser extends User implements Serializable {
	private static final long serialVersionUID = 4386550365501750905L;
	/**
	 * 用户ID
	 */
	@Getter
	private Integer id;

	/**
	 * 部门ID
	 */
	@Getter
	private Integer deptId;

	@Getter
	private String name;
	public SparkUser(Integer id, Integer deptId,  String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.id = id;
		this.deptId = deptId;
	}

}
