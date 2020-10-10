package com.slt.concurrency.myTest.thread;

import org.junit.Test;

/**
 * 测试Thread 的基本api
 */
public class ThreadState {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("1111");
        });
        System.out.println(thread.getState());
        thread.start();
        System.out.println(thread.getState());
        thread.join();
        System.out.println(thread.getState());
    }
}
