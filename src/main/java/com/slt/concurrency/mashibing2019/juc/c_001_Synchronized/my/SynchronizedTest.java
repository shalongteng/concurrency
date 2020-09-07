package com.slt.concurrency.mashibing2019.juc.c_001_Synchronized.my;

import org.junit.Test;

/**
 * 启动一万个线程 对数值10000 进行类减。
 * 查看最终结果是否为0；
 */
public class SynchronizedTest {
    public static int count = 10000;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread(() -> {
                    count--;
            });
            thread.start();
        }
        System.out.println(count);
    }

    @Test
    public void TestExit(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("666");
            }
        }).start();
        System.out.println("888");
    }
}
