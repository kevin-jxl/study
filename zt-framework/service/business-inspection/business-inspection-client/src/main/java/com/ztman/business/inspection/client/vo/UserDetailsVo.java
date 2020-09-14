package com.ztman.business.inspection.client.vo;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsVo  {
	 private static final long serialVersionUID = 1L;

	    private Integer id;
	    private String userName;
	    private String realName;
	    private String password;
}
