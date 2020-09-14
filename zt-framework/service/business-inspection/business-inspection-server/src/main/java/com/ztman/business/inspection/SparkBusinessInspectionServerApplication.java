package com.ztman.business.inspection;

import com.ztman.common.swagger.annotation.EnableSparkSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableSparkSwagger2
@EnableFeignClients
@SpringCloudApplication
@ServletComponentScan
@ComponentScan({"com.ztman.business.inspection"})
public class SparkBusinessInspectionServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SparkBusinessInspectionServerApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SparkBusinessInspectionServerApplication.class);
    }
}
