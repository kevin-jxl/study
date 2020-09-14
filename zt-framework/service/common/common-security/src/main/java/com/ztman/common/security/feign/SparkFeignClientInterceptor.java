package com.ztman.common.security.feign;

import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

/**
 * 扩展OAuth2FeignRequestInterceptor
 */
@Slf4j
public class SparkFeignClientInterceptor extends OAuth2FeignRequestInterceptor {
	private final OAuth2ClientContext oAuth2ClientContext;

	/**
	 * Default constructor which uses the provided OAuth2ClientContext and Bearer tokens
	 * within Authorization header
	 *
	 * @param oAuth2ClientContext provided context
	 * @param resource            type of resource to be accessed
	 */
	public SparkFeignClientInterceptor(OAuth2ClientContext oAuth2ClientContext
		, OAuth2ProtectedResourceDetails resource) {
		super(oAuth2ClientContext, resource);
		this.oAuth2ClientContext = oAuth2ClientContext;
	}


	/**
	 * Create a template with the header of provided name and extracted extract
	 *
	 * @param template
	 */
	@Override
	public void apply(RequestTemplate template) {
		if (oAuth2ClientContext != null
			&& oAuth2ClientContext.getAccessToken() != null) {
			super.apply(template);
		}
	}
}
