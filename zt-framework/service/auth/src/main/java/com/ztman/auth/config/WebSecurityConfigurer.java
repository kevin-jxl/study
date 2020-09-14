package com.ztman.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ztman.common.security.mobile.MobileLoginSuccessHandler;
import com.ztman.common.security.mobile.MobileSecurityConfigurer;
import com.ztman.common.security.util.SparkUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 认证相关配置
 */
@Primary
@Order(90)
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ClientDetailsService clientDetailsService;
	@Autowired
	private SparkUserDetailsService userDetailsService;
	@Lazy
	@Autowired
	private AuthorizationServerTokenServices defaultAuthorizationServerTokenServices;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers(
				"/actuator/**",
				"/oauth/removeToken",
				"/mobile/**",
				"/admin/druid/**",
				"/druid/**",
				"/demoproduct/**",
				"/demoorder/**"
			).permitAll()
			.anyRequest().authenticated()
			.and()
			.headers().frameOptions().disable()
			.and().csrf().disable()
			.apply(mobileSecurityConfigurer());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationSuccessHandler mobileLoginSuccessHandler() {
		return MobileLoginSuccessHandler.builder()
			.objectMapper(objectMapper)
			.clientDetailsService(clientDetailsService)
			.passwordEncoder(passwordEncoder())
			.defaultAuthorizationServerTokenServices(defaultAuthorizationServerTokenServices).build();
	}

	@Bean
	public MobileSecurityConfigurer mobileSecurityConfigurer() {
		return new MobileSecurityConfigurer(mobileLoginSuccessHandler()
			, userDetailsService);
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//		String encodingId = "MD5";
//		Map<String, PasswordEncoder> encoders = new HashMap();
//		encoders.put("MD5", new MessageDigestPasswordEncoder("MD5"));
//		return new DelegatingPasswordEncoder(encodingId, encoders);

	}

}
