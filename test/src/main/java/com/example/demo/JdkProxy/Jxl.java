package com.example.demo.JdkProxy;


import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 项目名称：testQRcode
 * 类 名 称：Jxl
 * 类 描 述：代理对象
 * 创建时间：2020/8/14 14:24
 * 创 建 人：纪星亮
 */
public class Jxl implements InvocationHandler {

    Object shop;


    private void preConfig() {
        System.out.println("接到需求，开始购买");
    }

    private void afterConfig() {
        System.out.println("购买完成");
    }

    public void setShop(Object shop) {
        this.shop = shop;
    }

    public Object getInvocationhandler() {
        return Proxy.newProxyInstance(shop.getClass().getClassLoader(), shop.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        preConfig();
        Object invoke = method.invoke(shop, args);
        afterConfig();
        return invoke;
    }
}