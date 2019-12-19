package com.slt.concurrency.myTest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

public class T01_ConcurrentMap {

    public static void main(String[] args) throws InterruptedException {
        Map<String, String> map = new ConcurrentHashMap<String, String>();//634 ms
//        Map<String, String> map = new ConcurrentSkipListMap<String, String>(); //高并发并且排序 775 ms

//        Map<String, String> map = new Hashtable<>();//607 ms
//        Map<String, String> map = new HashMap<String, String>();//480 ms
//        Map<String, String> map = Collections.synchronizedMap(new HashMap<>());//696 ms
//        Map<String, String> map = Collections.synchronizedMap(new TreeMap<>());//1295 ms

        Random random = new Random();
        Thread[] threads = new Thread[100];
        CountDownLatch latch = new CountDownLatch(threads.length);
        long start = System.currentTimeMillis();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                //每个线程 往map里加 一万个值
                for (int j = 0; j < 10000; j++){
                    map.put(Thread.currentThread().getName() + j, "a" + j);
                }

//                map.put(Thread.currentThread().getName(),"000");
                latch.countDown();
            });
        }
        //遍历启动一百个线程
        Arrays.asList(threads).forEach(t -> t.start());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start + " ms");
        System.out.println(map.size());
//        System.out.println(map);
    }
}