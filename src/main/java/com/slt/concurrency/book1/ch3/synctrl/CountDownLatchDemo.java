package com.slt.concurrency.book1.ch3.synctrl;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 倒数计时器
 * @author Geym
 *
 */
public class CountDownLatchDemo implements Runnable {
    static final CountDownLatch countDownLatch = new CountDownLatch(10);
    static final CountDownLatchDemo demo=new CountDownLatchDemo();
    @Override
    public void run() {
        try {
            //模拟检查任务
            Thread.sleep(new Random().nextInt(10)*1000);
            System.out.println(Thread.currentThread().getName()+"check complete");
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
    }
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(1);
        for(int i=0;i<10;i++){
            exec.submit(demo);
        }
        //等待检查
        countDownLatch.await();
        //发射火箭
        System.out.println("Fire!");
        exec.shutdown();
    }
}
