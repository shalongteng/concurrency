package com.slt.concurrency.example.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SynchronizedExample0 implements Runnable{

    @Override
    public void run() {
        test1();
    }
    // 修饰一个代码块
    public void test1() {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                log.info("test1 {} - {}", i);
            }
        }
    }

    // 修饰一个方法
    public synchronized void test2(int j) {
        for (int i = 0; i < 10; i++) {
            log.info("test2 {} - {}", j, i);
        }
    }

    public static void main(String[] args) {
        Runnable example1 = new SynchronizedExample0();
        Thread thread1 = new Thread(example1);
        SynchronizedExample0 example2 = new SynchronizedExample0();
        Thread thread2 = new Thread(example2);


        thread1.start();
        thread2.start();
    }


}
