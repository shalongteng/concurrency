package com.slt.concurrency.myTest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        Counter counter = new Counter();

        new Thread(new Runnable() {

            @Override
            public void run() {
                while (counter.getCount() >= 0) {
                    counter.desc1();
                }
            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                while (counter.getCount() >= 0) {
                    counter.desc2();
                }
            }
        }).start();
    }
}

class Counter {
    private int count = 100;
    private Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    boolean state = true;

    public void desc1() {
        lock.lock();// 上锁

        try {
            while (state)
                condition1.await();// 线程等待

            if (count >= 0) {
                System.out.println(Thread.currentThread().getName() + "--###->" + count);
                count--;
            }
            state = true;// 改变状态
            condition2.signal();// 唤醒调用了condition2.await()线程

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();// 释放锁
        }
    }

    public void desc2() {
        lock.lock();// 上锁

        try {
            while (!state)
                condition2.await();// 线程等待

            if (count >= 0) {
                System.out.println(Thread.currentThread().getName() + "--->" + count);
                count--;
            }
            state = false;// 改变状态
            condition1.signal();// 唤醒调用了condition1.await()线程

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();// 释放锁
        }
    }

    public int getCount() {
        return count;
    }
}
