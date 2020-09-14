package com.ztman.admin.api.config;

import feign.Contract;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * Created by Lenovo on 2018/10/29.
 */
public class DemoFeignConfigurationDc {
    @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("admin", "admin");
    }

}
