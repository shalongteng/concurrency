package com.slt.concurrency.myTest.thread;

import lombok.SneakyThrows;
import org.junit.Test;

/**
 * 测试Thread 的基本api
 */
public class ThreadApi {
    class ThreadA implements Runnable{

        @Override
        public void run() {
            System.out.println("ThreadA-----");
        }
    }
    class ThreadB implements Runnable{

        @Override
        public void run() {
            System.out.println("ThreadB-----");
        }
    }

    /**
     * 测试sleep方法
     * @throws InterruptedException
     */
    @Test
    public void testSleep() throws InterruptedException {
        new Thread(new ThreadA()).start();
        Thread.sleep(4000);
        System.out.println("666");
    }

    /**
     * thread1.start(); thread1需要启动,才能加入thread2
     * @throws InterruptedException
     */
    @Test
    public void testJoin() throws InterruptedException {
        Thread thread1 = new Thread(new ThreadA());
        Thread thread2 = new Thread(()->{
            try {
                thread1.join();
                System.out.println("thread2 run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
        Thread.sleep(2000);
    }

    /**
     * yield
     *     让出CPU，进入【等待队列】，返回到就绪状态
     *     抢不到cpu时间片的线程就会被挂起。
     */
    @Test
    public void testYield() throws InterruptedException {
        Thread thread1 = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                if(i % 2 == 0)
                    Thread.yield();
                System.out.println("thread1: "+i);
            }
        });
        Thread thread2 = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                if(i % 2 == 0)
                    Thread.yield();
                System.out.println("thread2: "+i);
            }
        });
        thread1.start();
        thread2.start();

    }
}
