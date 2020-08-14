package com.example.demo.Lock;

import lombok.Data;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * 项目名称：testQRcode
 * 类 名 称：LockSuo
 * 类 描 述：TODO
 * 创建时间：2020/8/11 16:26
 * 创 建 人：纪星亮
 */
// 1、实现lock接口
@Data
public class LockJxl implements Lock {
    // 身份证登记 原子性问题
    AtomicReference<Thread> reference = new AtomicReference<Thread>();

    // 队列（放阻塞线程）
    LinkedBlockingQueue<Thread> waites = new LinkedBlockingQueue<>();

    // 2、实现lock方法
    @Override
    public void lock() {
        /*
            CAS原理存入当前线程
            第一个参数：期望值
            第二个参数：如果期望值正确，修改的值
        */
        while (!reference.compareAndSet(null, Thread.currentThread())) {
            waites.add(Thread.currentThread());
            LockSupport.park(); // 没有指定线程，就是当前线程阻塞
            waites.remove(Thread.currentThread());
        }
    }

    // 3、实现unlock方法
    @Override
    public void unlock() {
        // CAS解锁，只有持有锁者才能解锁
        if (reference.compareAndSet(Thread.currentThread(), null)) {
            // 唤醒阻塞线程
            for (Object obj : waites.toArray()) {
                Thread t = (Thread) obj;
                LockSupport.unpark(t);
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {

        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {

        return false;
    }

    @Override
    public Condition newCondition() {

        return null;
    }
}