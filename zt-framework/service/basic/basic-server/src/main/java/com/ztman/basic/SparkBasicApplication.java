package com.ztman.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients({"com.ztman.basic.api.feign"})
public class SparkBasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SparkBasicApplication.class, args);
	}
}
