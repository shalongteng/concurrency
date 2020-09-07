package com.slt.concurrency.mashibing2019.juc.c_000.my;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolException {
       ExecutorService threadPool = Executors.newFixedThreadPool(5);
       public static void main(String[] args) {
              ThreadPoolException t = new ThreadPoolException();
              t.futureGet();
       }
       void futureGet() {
              for (int i = 0; i < 5; i++) {
                   Future future = threadPool.submit(() -> {
                          System.out.println("current thread name" + Thread.currentThread().getName());
                          Object object = null;
                          System.out.print("result## " + object.toString());
                   });
                   try {
                          future.get();
                   } catch (Exception e) {
                           System.out.println(Thread.currentThread().getName() + "异常");
                           // 让主线程多等一段时间,便于观察.
                           try {
                                Thread.sleep(10000);
                           } catch (InterruptedException e1) {
                                e1.printStackTrace();
                           }
                           //主线程终止
                           throw new RuntimeException(Thread.currentThread().getName() + "异常");
                }
        }
     }
}
