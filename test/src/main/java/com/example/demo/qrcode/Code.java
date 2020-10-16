package com.example.demo.qrcode;

import com.example.demo.mapper.CodeMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private CodeMapper codeMapper;

    @GetMapping
    public List<HashMap> getcode(@RequestParam(required = false) Map<String, Object> params) {
        return codeMapper.list(params);
    }

    @GetMapping("test")
    public String testJenkins() {
        return "部署成功";
    }
}