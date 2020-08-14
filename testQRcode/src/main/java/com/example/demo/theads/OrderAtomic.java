package com.example.demo.theads;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 项目名称：testQRcode
 * 类 名 称：order
 * 类 描 述：TODO
 * 创建时间：2020/8/7 14:05
 * 创 建 人：纪星亮
 */
public class OrderAtomic {
    public AtomicInteger i = new AtomicInteger(60000);
    public void a() {
        i.decrementAndGet();
    }

    public static void main(String[] arge) throws InterruptedException {
        OrderAtomic order = new OrderAtomic();
        // 模拟多线程
        for (int i = 0; i < 6; i ++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j ++) {
                    order.a();
                }
            }).start();
        }
        Thread.sleep(5000);
        System.out.println("i=" + order.i);
    }
}