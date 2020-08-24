package com.slt.concurrency.springbootThreadPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author qjwyss
 * @date 2018/10/12
 * @description
 */
@Service
public class AsyncServiceImpl implements AsyncService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);
    private ExecutorService executorService = Executors.newFixedThreadPool(2);


    // 直接使用@Async注解，并声明线程池，即可使用多线程；
    @Async("taskExecutor")
    @Override
    public void executeAsync() {
        logger.info("start executeAsync");
        try {
            Thread.sleep(10000);
            System.out.println("当前运行的线程名称：" + Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("end executeAsync");
    }

    /**
     * 方法体 中代码相当于run
     */
    @Async("taskExecutor")
    @Override
    public void executeAsync2() {
        logger.info("start executeAsync2");
        try {
            System.out.println("当前运行的线程名称：" + Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("end executeAsync2");
    }

    @Override
    public void executeAsync3() {
        logger.info("start executeAsync3333333333333");
        try {
            for (int i = 0; i < 5; i++) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("当前运行的线程名称###：" + Thread.currentThread().getName());
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("end executeAsync33333333333333");
    }
}
