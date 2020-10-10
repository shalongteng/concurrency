package com.slt.concurrency.mashibing2019.juc.c_020_LockAndTools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 循环栅栏
 * 当栅栏满了，触发一个动作
 */
public class T07_TestCyclicBarrier {
    public static void main(String[] args) {
        //CyclicBarrier barrier = new CyclicBarrier(20);

        //当栅栏满了，触发一个动作
        CyclicBarrier barrier = new CyclicBarrier(20, () -> System.out.println("满人，发车"));

        /*CyclicBarrier barrier = new CyclicBarrier(20, new Runnable() {
            @Override
            public void run() {
                System.out.println("满人，发车");
            }
        });*/

        for(int i=0; i<100; i++) {
            new Thread(()->{
                try {
                    //100个线程 会满5次
                    barrier.await();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
