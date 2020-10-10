package com.example.demo.JdkProxy;

/**
 * 项目名称：testQRcode
 * 类 名 称：TestShop
 * 类 描 述：TODO
 * 创建时间：2020/8/14 14:32
 * 创 建 人：纪星亮
 */
public class TestShop {
    public static void main(String[] args) {
        // 1、创建目标对象
        ShopFactory newShop = new NewShop();

        // 2、创建代理对象，并传入具体实现业务的目标对象
        Jxl jxl = new Jxl();
        jxl.setShop(newShop);
        ShopFactory one = (ShopFactory) jxl.getInvocationhandler();
        // 3、操作方法
        one.shopDog(5);

        System.out.println("----------------------");

        Shop2Factory newShop2 = new NewShop2();
        jxl.setShop(newShop2);
        Shop2Factory two = (Shop2Factory) jxl.getInvocationhandler();
        two.solo(3);
    }
}