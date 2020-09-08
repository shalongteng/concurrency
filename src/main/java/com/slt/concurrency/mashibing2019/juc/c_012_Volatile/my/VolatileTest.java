package com.slt.concurrency.mashibing2019.juc.c_012_Volatile.my;

import org.junit.runner.RunWith;

public class VolatileTest implements Runnable {
    private volatile boolean flag = false;
    @Override
    public void run() {
        System.out.println("start");
        while (true){
            if(flag){
                System.out.println("i see the change ");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileTest volatileTest = new VolatileTest();
        new Thread(volatileTest).start();
        //要睡一小会 确保线程启动在 设置flag 之前执行
        Thread.sleep(100);
        volatileTest.flag = true;
    }
}
