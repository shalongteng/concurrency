package com.slt.concurrency.myTest.join;

public class JoinTest2 {
    /**
     * @Description:  如果调用某个thread的join方法，那么当前线程就会被阻塞，直到thread线程执行完毕，
     * 当前线程才能继续执行。join的原理是，不断的检查thread是否存活，如果存活，那么让当前线程一直wait，
     * 直到thread线程终止，线程的this.notifyAll 就会被调用。
     *
     * 注：t.join()方法只会使主线程进入等待池并等待t线程执行完毕后才会被唤醒。
     * 并不影响同一时刻处在运行状态的其他线程。
     * @Param:
     * @return:
     * @Author: shalongteng
     * @Date: 2019-12-08
     */
    public static void main(String[] args) throws InterruptedException {

        Employee a = new Employee("A", 1000);
        Employee b = new Employee("B", 2000);
        Employee c = new Employee("C", 5000);

        long l1 = System.currentTimeMillis();
        //b 启动一个线程，开始准备
        b.start();
        //c 启动一个线程，开始准备
        c.start();

        //会使main线程阻塞 两秒左右
        b.join();
        //因为c 已经运行了两秒左右了，所以他只能阻塞main线程 3秒左右
        c.join();
        long l2 = System.currentTimeMillis();
        System.out.println(l2-l1);
        System.out.println("main Thread---> B,C准备完成");
        a.start();
    }
}
