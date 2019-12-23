package com.slt.concurrency.myTest.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 响应中断
 */
public class ReentrantLockTest03 {
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread thread0 = new Thread(new ThreadDemo(lock1, lock2,"thread0"));//该线程先获取锁1,再获取锁2
        Thread thread1 = new Thread(new ThreadDemo(lock2, lock1,"thread1"));//该线程先获取锁2,再获取锁1
        thread0.start();
        thread1.start();
        //是第一个线程中断 被中断的线程将抛出异常，而另一个线程将能获取锁后正常结束。
        thread0.interrupt();
    }

    static class ThreadDemo implements Runnable {
        String name;
        Lock firstLock;
        Lock secondLock;
        public ThreadDemo(Lock firstLock, Lock secondLock,String name) {
            this.firstLock = firstLock;
            this.secondLock = secondLock;
            this.name = name;
        }
        @Override
        public void run() {
            try {
                firstLock.lockInterruptibly();
                TimeUnit.MILLISECONDS.sleep(100);//更好的触发死锁
                secondLock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                firstLock.unlock();
                secondLock.unlock();
                System.out.println(name+"正常结束!");
            }
        }
    }
}