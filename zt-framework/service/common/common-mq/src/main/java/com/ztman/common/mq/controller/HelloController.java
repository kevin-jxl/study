package com.ztman.common.mq.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lenovo on 2018/10/23.
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String  hello(){
        return "hello";
    }
}
