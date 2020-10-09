package com.example.demo.qrcode;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目名称：testQRcode
 * 类 名 称：Code
 * 类 描 述：TODO
 * 创建时间：2020/9/14 15:57
 * 创 建 人：纪星亮
 */
@RestController
@RequestMapping("code")
public class Code {

    @GetMapping("get")
    public String getcode(@RequestParam String name) {
        return "恭喜=" + name;
    }
}