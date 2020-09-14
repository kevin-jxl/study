package com.ztman.common.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * 默认配置类
 *
 * @author liuyadu
 */
@Slf4j
@Configuration
public class AutoConfiguration {


    /**
     * 自定义注解扫描
     *
     * @return
     */
    public static AnnotationScan annotationScan() {
        AnnotationScan scan = new AnnotationScan();
        return scan;
    }
}
