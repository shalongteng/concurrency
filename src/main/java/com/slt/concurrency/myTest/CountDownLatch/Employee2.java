package com.slt.concurrency.myTest.CountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: shalongteng
 * @Date: 2019-12-08 17:58
 */
public class Employee2 extends Thread{
    private String employeeName;

    private long time;

    private CountDownLatch countDownLatch;

    public Employee2(String employeeName,long time, CountDownLatch countDownLatch){
        this.employeeName = employeeName;
        this.time = time;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            System.out.println(employeeName+ " 第一阶段开始准备");
            Thread.sleep(time);
            System.out.println(employeeName+" 第一阶段准备完成");

            countDownLatch.countDown();

            System.out.println(employeeName+ " 第二阶段开始准备");
            Thread.sleep(time);
            System.out.println(employeeName+" 第二阶段准备完成");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
