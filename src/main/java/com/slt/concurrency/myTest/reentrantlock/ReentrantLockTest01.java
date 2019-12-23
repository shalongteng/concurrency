package com.slt.concurrency.myTest.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest01 {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        //可重入锁
        for (int i = 1; i <= 3; i++) {
            lock.lock();
        }

        for(int i=1;i<=3;i++){
            try {

            } finally {
                lock.unlock();
            }
        }
    }
}