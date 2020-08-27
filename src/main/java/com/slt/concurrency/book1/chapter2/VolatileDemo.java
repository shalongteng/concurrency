package com.slt.concurrency.book1.chapter2;

/**
 */
public class VolatileDemo {
    static volatile int i = 0;

    public static class PlusTask implements Runnable {
        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                i++;
            }
        }
    }

    /**
     *
     */
    public static void main(String args[]) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[100];
        for (int j = 0; j < 10; j++) {
            threads[j] = new Thread(new PlusTask());
            threads[j].start();
        }
        for (int j = 0; j < 10; j++) {
            threads[j].join();
        }
        System.out.println(i);
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("time:" + time);
    }
}
