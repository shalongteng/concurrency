package com.slt.concurrency.myTest;

import javax.xml.transform.Source;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

public class T02_CopyOnWriteArrayList {

    public static void main(String[] args) throws InterruptedException {
        //这个会出并发问题！报错:ArrayIndexOutOfBoundsException
//        List<String> lists = new ArrayList<>();
//        List<String> lists = new Vector();//280 ms
        List<String> lists = new CopyOnWriteArrayList<>();//5393 ms

        Thread[] threads = new Thread[100];
        CountDownLatch latch = new CountDownLatch(threads.length);
        Random r = new Random();
        long start = System.currentTimeMillis();
        for (int i = 0; i < threads.length; i++) {
            //测试核心代码：
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        lists.add("a" + r.nextInt(10000));
                    }
                    latch.countDown();
                }
            };
            threads[i] = new Thread(task);
        }
        //遍历启动一百个线程
        Arrays.asList(threads).forEach(t -> t.start());
        //多线程向该容器中不断加入数据。
        latch.await();
        long end = System.currentTimeMillis();
        System.out.println(end - start + " ms");
        System.out.println(lists.size());
    }
}