package com.example.demo.JdkProxy;

/**
 * 项目名称：testQRcode
 * 类 名 称：NewShop2
 * 类 描 述：TODO
 * 创建时间：2020/8/14 15:33
 * 创 建 人：纪星亮
 */
public class NewShop2 implements Shop2Factory {

    @Override
    public void solo(int num) {
        System.out.println("海外牛奶"+num);
    }
}