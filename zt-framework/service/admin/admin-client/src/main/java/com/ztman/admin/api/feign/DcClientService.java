package com.ztman.admin.api.feign;

import com.ztman.admin.api.config.DemoFeignConfigurationDc;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Lenovo on 2018/8/6.
 */
@Component
//@FeignClient("eureka-client")
@FeignClient(name = "product", configuration=DemoFeignConfigurationDc.class)
public interface DcClientService {
    @GetMapping("/dc")
    String consumer();
        //@RequestLine("GET /dc")
//        @RequestLine("GET /dc/")
//        String consumer();
}
