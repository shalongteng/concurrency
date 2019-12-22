package com.slt.concurrency.myTest.jdk.CountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * url：https://www.jianshu.com/p/795151ac271b
 * url：https://www.jianshu.com/p/e233bb37d2e6
 *
 * 注：调用join方法需要等待thread执行完毕才能继续向下执行,而CountDownLatch只需要检查计数器的值
 * 为零就可以继续向下执行，相比之下，CountDownLatch更加灵活一些，可以实现一些更加复杂的业务场景。
 *
 *countDownLatch这个类使一个线程等待其他线程各自执行完毕后再执行。
 * 是通过一个计数器来实现的，计数器的初始值是线程的数量。每当一个线程执行完毕后，计数器的值就-1，
 * 当计数器的值为0时，表示所有线程都执行完毕，然后在闭锁上等待的线程就可以恢复工作了。

 * @Auther: shalongteng
 * @Date: 2019-12-08
 * @Description: 
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Employee a = new Employee("A", 3000,countDownLatch);
        Employee b = new Employee("B", 3000,countDownLatch);
        Employee c = new Employee("C", 4000,countDownLatch);
        
        b.start();
        c.start();
        //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
        countDownLatch.await();
        System.out.println("B,C准备完成");
        a.start();
    }
}