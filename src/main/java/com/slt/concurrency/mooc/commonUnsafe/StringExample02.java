package com.slt.concurrency.mooc.commonUnsafe;

import com.slt.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @ProjectName: concurrency
 * @Package: com.slt.concurrency.example.commonUnsafe
 * @ClassName: StringExample01
 * @Author: shalongteng
 * @Description: ${description}
 * @Date: 2019-12-17 21:15
 * @Version: 1.0
 */
@Slf4j
@ThreadSafe
public class StringExample02 {
    //请求总数
    public static int clientTotal = 5000;
    //线程总数
    public static int threadTotal = 200;
    //计数的变量
    public static StringBuffer stringBuffer = new StringBuffer();

    public static void main(String[] args) throws InterruptedException {
        //线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        //计数器闭锁
        CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        //信号量
        Semaphore semaphore = new Semaphore(threadTotal);

        //循环启动5000 个线程，但是同时限流只能有200个线程 进行操作
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    update();
                    semaphore.release();
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        //main 线程等待
        countDownLatch.await();
        //线程池终止
        executorService.shutdown();
        //输出count 的值
        log.info("size:{}", stringBuffer.length());
    }

    private static void update() {
        stringBuffer.append("1");
    }
}
