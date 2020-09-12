package com.example.demo.JdkProxy;

/**
 * 项目名称：testQRcode
 * 类 名 称：NewShop
 * 类 描 述：TODO
 * 创建时间：2020/8/14 14:31
 * 创 建 人：纪星亮
 */
public class NewShop implements ShopFactory {
    @Override
    public void shopDog(int num) {
        System.out.println("目标对象购买了 "+num+" 只狗狗");
    }
}