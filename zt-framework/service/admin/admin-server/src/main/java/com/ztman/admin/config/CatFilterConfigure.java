package com.ztman.admin.config;

//import com.dianping.cat.servlet.CatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Cat 过滤器初始化
 */
public class CatFilterConfigure{
    @Bean
    public FilterRegistrationBean catFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
//        CatFilter filter = new CatFilter();
//        registration.setFilter(filter);
//        registration.addUrlPatterns("/*");
//        registration.setName("cat-filter");
//        registration.setDispatcherTypes(DispatcherType.REQUEST,DispatcherType.FORWARD);
        registration.setOrder(1);
        return registration;
    }

}
