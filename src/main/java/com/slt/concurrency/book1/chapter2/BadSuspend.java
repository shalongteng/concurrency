package com.slt.concurrency.book1.chapter2;

import com.slt.concurrency.book1.chapter3.LockSupportIntDemo;

import java.util.concurrent.locks.LockSupport;

public class BadSuspend {
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super.setName(name);
        }

        public void run() {
            synchronized (u) {
                System.out.println("in " + getName());
                Thread.currentThread().suspend();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(100);
        t2.start();
//        Thread.sleep(100); //如果没有这行代码，t2.resume很可能落后于   t2.suspend t2就一直挂起
        t1.resume();
        t2.resume();
        t1.join();
        t2.join();
    }
}
