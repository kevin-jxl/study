package com.ztman.common.security.feign;

import feign.RequestInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

/**
 * feign 拦截器传递 header 中oauth token，
 * 使用hystrix 的信号量模式
 */
@Configuration
@AllArgsConstructor
@ConditionalOnProperty("security.oauth2.client.client-id")
public class SparkFeignClientConfiguration {

	@Bean
	public RequestInterceptor oauth2FeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext,
															OAuth2ProtectedResourceDetails resource) {
		return new SparkFeignClientInterceptor(oAuth2ClientContext, resource);
	}
}
