package com.slt.concurrency.myTest.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//有问题的程序代码
public class Main01 {
    public static void main(String[] args) {
        Counter counter = new Counter();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (counter.getCount() >= 0)
                    counter.desc();
            }
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
    }
}

//class Counter {
//    private int count = 100;
//
//    public void desc() {
//        System.out.println(Thread.currentThread().getName() + "--->" + count);
//        count--;
//    }
//
//    public int getCount() {
//        return count;
//    }
//}

/**
 * 上面代码还是有问题的，那就是锁的释放，如果在上锁了，后面的代码抛出异常没能释放锁，你说完不完蛋！！？所以锁的释放一定要在try-finally块
 * 的finally中，就像是JDBC中释放数据库连接那样。这一点还是synchronized比较方便，不用我们自己释放锁。
 */
class Counter {
    private int count = 100;
    private Lock lock = new ReentrantLock();

    public void desc() {
        lock.lock();//上锁

        if (count >= 0){
            System.out.println(Thread.currentThread().getName() + "--->" + count);
            count--;
        }

        lock.unlock();//释放锁
    }

    public int getCount() {
        return count;
    }
}

