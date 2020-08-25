package com.slt.concurrency.mooc.blockingQueue;

import java.util.concurrent.SynchronousQueue;

/**
 * @Classname SyncQueue
 * @Date 2020/1/3 10:16
 * @Created by slt
 */
public class SyncQueue {
    /**
     * 没有容量的一个特殊队列
     * 执行阻塞的方法 put 和 take的时候正常，所有的put方法有一个阻塞的公平队列或者非公平队列，所有的take操作也有一个类似的队列
     * 执行非阻塞的方法 add()和remove()方法时，必须有对应的take和put方法阻塞着，不然就会报错
     * peek(返回首位但是不删除元素)永远返回null，因为不存储元素
     */
    public static void main(String[] args) throws Exception {

        final SynchronousQueue<String> queue = new SynchronousQueue<>();//初始化不能带长度
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName()+"已经启动");
                    //线程1在获取，这是阻塞的，当线程2一添加，线程1就获取，因为SynchronousQueue是没有容量的
                    String str = queue.take();
                    System.out.println("take:"+str);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName()+"已经启动");

                    queue.put("abcde");
                } catch (InterruptedException e) {
                }
                System.out.println("add:"+"abcde");
            }
        });
        t3.start();
    }
}
