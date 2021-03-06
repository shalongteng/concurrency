package com.slt.concurrency.mashibing2019.juc.c_000_Thread;

/**
 *   继承Thread类
 *   实现Runnabale
 *   jdk8以后 lambda表达式
 */
public class T02_HowToCreateThread {
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hello MyThread!");
        }
    }

    static class MyRun implements Runnable {
        @Override
        public void run() {
            System.out.println("Hello MyRun!");
        }
    }

    public static void main(String[] args) {
        //第一种
        new MyThread().start();
        //第二种
        new Thread(new MyRun()).start();
        //第三种
        new Thread(()->{
            System.out.println("Hello Lambda!");
        }).start();
    }

}

//请你告诉我启动线程的三种方式
// 1：Thread
// 2: Runnable
// 3:Executors.newCachedThrad
