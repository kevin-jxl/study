package com.example.demo;

import com.example.demo.spike.service.GoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMiaoSha {

	public static final int GOODS_ID = 1;
	public static final int GOODS_NUM = 100;
    public static int success_goods_people = 0;
    public static int success_goods_num = 0;
	public static final int USER_NUM = 1000;
	public static final int USER_SHOP_NUM = 3;

    public static CountDownLatch countDownLatch = new CountDownLatch(USER_NUM);

    public static CyclicBarrier cyc = new CyclicBarrier(USER_NUM + 1);

	@Resource
	GoodsService goodsService;

	@Test
	public void a() throws InterruptedException, BrokenBarrierException {
        goodsService.init(GOODS_ID, GOODS_NUM);
        long start = System.currentTimeMillis();

        // 模拟并发
        for (int i = 0; i < USER_NUM; i ++) {
            new Thread(new UserRequset(GOODS_ID, USER_SHOP_NUM)).start();
            countDownLatch.countDown();
        }

//        Thread.currentThread().sleep(1000);
        cyc.await();

        long end = System.currentTimeMillis();
        System.out.println("时长：" + (end - start));
        System.out.println("成功购买人数：" + success_goods_people);
        System.out.println("成功购买数量：" + success_goods_num);
        System.out.println("剩余数量：" + goodsService.selectNumById(GOODS_ID));
    }

    public class UserRequset implements Runnable {
        int id;
        int num;
        public UserRequset(int id, int num) {
            this.id = id;
            this.num = num;
        }

        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (goodsService.updateGoodsNum(id, num)) {
                synchronized (countDownLatch) {
                    success_goods_people ++;
                    success_goods_num += num;
                }
            }

            try {
                cyc.await();
            } catch (InterruptedException e) {

            } catch (BrokenBarrierException e) {

            }
        }
    }

}
