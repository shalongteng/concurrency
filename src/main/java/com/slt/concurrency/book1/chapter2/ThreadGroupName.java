package com.slt.concurrency.book1.chapter2;

/**
 * Created by 13 on 2017/5/4.
 */
public class ThreadGroupName implements Runnable {
    @Override
    public void run() {
        String groupAndName = Thread.currentThread().getThreadGroup().getName() + "-" + Thread.currentThread().getName();
        while (true) {
            System.out.println("I am " + groupAndName);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        ThreadGroup tg = new ThreadGroup("PrintGroup");
        Thread t1 = new Thread(tg, new ThreadGroupName(), "T1");
        Thread t2 = new Thread(tg, new ThreadGroupName(), "T2");
        t1.start();
        t2.start();
        System.out.println(tg.activeCount());
        Thread t3 = new Thread(tg, new ThreadGroupName(), "T3");
        t3.start();
        //以获得活动线程的总数
        System.out.println(tg.activeCount());
        //list（）方法可以打印这个线程组中所有的钱程信息，
        tg.list();
    }
}
