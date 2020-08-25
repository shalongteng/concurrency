package com.slt.concurrency.mooc.commonUnsafe;

import com.slt.concurrency.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @ProjectName: concurrency
 * @Package: com.slt.concurrency.example.commonUnsafe
 * @ClassName: DateFormatExample01
 * @Author: shalongteng
 * @Description: ${description}
 * @Date: 2019-12-17 21:31
 * @Version: 1.0
 */
@ThreadSafe
@Slf4j
public class DateFormatExample03 {
    //请求总数
    public static int clientTotal = 5000;
    //线程总数
    public static int threadTotal = 200;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

    public static void main(String[] args) throws InterruptedException {
        //线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        //计数器闭锁
        CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        //信号量
        Semaphore semaphore = new Semaphore(threadTotal);

        //循环启动5000 个线程，但是同时限流只能有200个线程 进行操作
        for (int i = 1; i <= clientTotal; i++) {
            final  int count = i;
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    update(count);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        //main 线程等待
        countDownLatch.await();
        //线程池终止
        executorService.shutdown();
    }

    private static void update(int i) {
        log.info("{}-->{}",i,DateTime.parse("2019-12-17",dateTimeFormatter).toDate());

    }
}
