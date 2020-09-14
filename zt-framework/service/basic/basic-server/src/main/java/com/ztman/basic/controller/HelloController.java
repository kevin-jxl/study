package com.ztman.basic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lenovo on 2018/9/14.
 */
@RestController
public class HelloController {

    @RequestMapping("/info")
    public  String info(){
        System.out.println("脚手架");
        return "标准开发脚手架";
    }
}
