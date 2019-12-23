package com.slt.concurrency.example.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
public class CyclicBarrierExample1 {

    private final static int threadCount = 20;

    public static void main(String[] args) throws Exception {

        ExecutorService exec = Executors.newCachedThreadPool();


        CyclicBarrier cyclicBarrier = new CyclicBarrier(20);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "开始等待其他线程");
                    cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName() + "开始执行");
                    // 工作线程开始处理，这里用Thread.sleep()来模拟业务处理
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + "执行完毕");
                } catch (Exception e) {
                    log.error("exception", e);
                }finally {
                }
            });
        }
        exec.shutdown();
        log.info("why do not you and suzzy go and play in your bed room");
    }

    private static void test(int threadNum) throws Exception {
        log.info("{}", threadNum);
        Thread.sleep(1000);
    }
}
