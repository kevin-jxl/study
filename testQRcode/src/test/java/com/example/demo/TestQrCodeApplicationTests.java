package com.example.demo;

import com.example.demo.aop.TestAop.AopService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestQrCodeApplicationTests {

	@Resource
	AopService aopService;

	@Test
	public void a() {
		aopService.findById("123");
	}

}
