package com.ztman.codegen;

import com.ztman.common.swagger.annotation.EnableSparkSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @date 2018/07/29
 * 代码生成模块
 */
@EnableSparkSwagger2
@SpringCloudApplication
public class CodeGenApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CodeGenApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CodeGenApplication.class);
	}
}
