package com.ztman.admin;
import com.ztman.common.swagger.annotation.EnableSparkSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

/**
 * 用户统一管理系统
 * @author Administrator
 */
@EnableSparkSwagger2
@EnableFeignClients
@SpringCloudApplication
@ServletComponentScan
@Primary
@ComponentScan({"com.ztman.admin"})
public class AdminServerApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(AdminServerApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AdminServerApplication.class);
	}
}
