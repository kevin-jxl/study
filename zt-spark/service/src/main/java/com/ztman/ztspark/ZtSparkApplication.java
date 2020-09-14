package com.ztman.ztspark;

import com.ztman.common.swagger.annotation.EnableSparkSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

@EnableSparkSwagger2
@EnableFeignClients
@SpringCloudApplication
@ServletComponentScan
@Primary
@ComponentScan({"com.ztman.ztspark"})
public class ZtSparkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZtSparkApplication.class, args);
    }

}
