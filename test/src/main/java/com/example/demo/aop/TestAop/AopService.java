package com.example.demo.aop.TestAop;

import com.example.demo.aop.CachAndLogJxl;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

/**
 * 项目名称：testQRcode
 * 类 名 称：AopService
 * 类 描 述：TODO
 * 创建时间：2020/8/13 16:27
 * 创 建 人：纪星亮
 */
@Service
public class AopService {

    @CachAndLogJxl(key = "#id", name = "testCach", isCach = true)
    public void findById(String id) {
        System.out.println("findById id = " + id);
    }
}