package com.ztman.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Lenovo on 2018/9/14.
 */
@Controller
public class ViewController {

    @RequestMapping("/index")
    public String index(){
        return "/index";
    }
}
