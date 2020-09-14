package com.ztman.admin.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.ztman.common.data.datascope.DataScopeInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@Slf4j
@MapperScan("com.ztman.admin.mapper")
public class MybatisPlusConfigurer {
    /**
     * 分页插件
     *
     * @return PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 数据权限插件
     *
     * @param dataSource 数据源
     * @return DataScopeInterceptor
     */
    @Bean
    @ConditionalOnMissingBean
    public DataScopeInterceptor dataScopeInterceptor(DataSource dataSource) {
        log.info("初始化数据权限插件");
        return new DataScopeInterceptor(dataSource);
    }
}
