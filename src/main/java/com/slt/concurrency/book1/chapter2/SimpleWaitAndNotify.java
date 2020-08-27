package com.slt.concurrency.book1.chapter2;

/**
 * wait 和 notify 需要在synchronized 里写
 * 也就是 wait 和 notify 需要获取对象的锁，才能调用，如果没有获取锁，会抛出IllegalMonitorStateException
 */
public class SimpleWaitAndNotify {
    final static Object object = new Object();

    public static class Thread1 extends Thread {
        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis() + ":thread1 start !");
                try {
                    System.out.println(System.currentTimeMillis() + ":thread1 wait for object !");
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + ":thread1 end!");
            }
        }
    }

    public static class Thread2 extends Thread {
        public void run() {
            synchronized (object) {
                System.out.println(System.currentTimeMillis() + ":thread2 start ! notify one thread");
                object.notify();

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + ":thread2 end!");
            }
        }
    }

    public static void main(String args[]) {
        Thread thread1 = new Thread1();
        Thread thread2 = new Thread2();
        thread1.start();
        thread2.start();
    }

}
