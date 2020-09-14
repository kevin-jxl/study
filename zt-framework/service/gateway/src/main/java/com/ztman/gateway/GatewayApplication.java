package com.ztman.gateway;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 网关应用
 */
@SpringCloudApplication
public class GatewayApplication
		//extends SpringBootServletInitializer
{

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
/*	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(sparkGatewayApplication.class);
	}*/
}
