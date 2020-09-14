package com.ztman.common.security.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**

 * 自定义OAuth2Exception
 */
public class SparkAuth2Exception extends OAuth2Exception {

	public SparkAuth2Exception(String msg) {
		super(msg);
	}
}
