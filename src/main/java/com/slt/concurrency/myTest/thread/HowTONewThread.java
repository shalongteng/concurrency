package com.slt.concurrency.myTest.thread;

import org.junit.Test;

import javax.xml.transform.Source;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * #2、创建线程几种方法
 * 继承Thread类
 * 实现Runnabale
 * jdk8以后 lambda表达式
 * <p>
 * 启动线程三种方式
 * 继承Thread类
 * 实现Runnabale
 * 通过线程池来启动
 */
public class HowTONewThread {
    /**
     * 继承Thread类
     */
    @Test
    public void inheritThread() throws InterruptedException {
        class ThreadA extends Thread {
            @Override
            public void run() {
                System.out.println("继承Thread类");
            }
        }
        //调用run方法,属于方法调用
        new ThreadA().run();
        new ThreadA().start();
        //他不会等ThreadA 线程执行为完毕
        Thread.sleep(1000);
    }

    /**
     * 实现Runnable接口
     * @throws InterruptedException
     */
    @Test
    public void implementsRunnable() throws InterruptedException {
        class ThreadA implements Runnable {
            @Override
            public void run() {
                System.out.println("实现Runnable接口");
            }
        }
        //调用start方法
        new Thread(new ThreadA()).start();
        Thread.sleep(1000);
    }

    /**
     * lambda表达式
     * @throws InterruptedException
     */
    @Test
    public void lambdaThread() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("匿名内部类");
            }
        }).start();

        new Thread(()->{
            System.out.println("lambda表达式");
        }).start();
        Thread.sleep(1000);
    }

    /**
     * 通过线程池 启动线程
     * @throws InterruptedException
     */
    @Test
    public void excutors() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(()->{
            System.out.println("666");
        });
        TimeUnit.SECONDS.sleep(1);
    }

}

/**
 * java源文件中只能有一个public类, 而且如果有public类的话，这个文件的名字要和这个类的名字一样
 */
class A {
    /**
     * 内部类会产生两个 class 文件，
     * 一个  A.class,
     * 一个  A$B.class
     */
    class B {
    }

    public static void main(String[] args) {
        System.out.println("44");
    }

}
