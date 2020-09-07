package com.slt.concurrency.mashibing2019.juc.c_000.my;

import org.junit.Test;

/**
 * new Thread()  不加参数 也可以启动一个线程，启动一个空的线程，什么都不做
 */
public class ThreadTest {
    /**
     * new Thread()  不加参数 也可以启动一个线程，启动一个空的线程，什么都不做
     * setDaemon 会抛出异常
     */
    @Test
    public void TestThread() throws InterruptedException {
        Thread thread = new Thread();
        thread.setName("线程1");
        //在线程start()之前setDaemon(true)，否则会抛出一个IllegalThreadStateException异常。
        thread.setDaemon(true);
        thread.start();
        System.out.println(thread.isDaemon());
        System.out.println(thread.isAlive());
        System.out.println(thread.getName()+" 的状态是："+thread.getState());
        //如果不加join 那么 thread 状态就会是 terminate
        thread.join();
        System.out.println("当前线程名称是："+Thread.currentThread().getName());
    }

    /**
     * main 线程结束，其他现在会wait
     * @throws InterruptedException
     */
    @Test
    public void TestRunnable() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    System.out.println("666");
                }
            }
        },"TestRunnable2");
        thread.setDaemon(true);
        thread.start();
        //如果不加join main线程如果先行结束，那么 将不会输出666
//        thread.join();
        System.out.println(thread.isDaemon());
    }

    /**
     * 用户通过使用线程组的概念批量管理线程，如批量停止或挂起等。
     *
     * 线程组也有 父子关系
     * 当前函数执行流程里 属于同一个线程组 main，如果在当前方法新建一个线程组
     * 就是main线程组的 子线程组
     */
    @Test
    public void TestThreadGroup() throws InterruptedException {
        //获取当前线程的group
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        //在当前线程执行流中新建一个Group1
        ThreadGroup group1 = new ThreadGroup("Group1");
        //Group1的父线程,就是main线程所在Group
        System.out.println(group1.getParent() == currentGroup);
        //定义Group2, 指定group1为其父线程
        ThreadGroup group2 = new ThreadGroup(group1, "Group2");
        System.out.println(group2.getParent() == group1);
    }

    /**
     *  ThreadGroup.list();
     * 用于将信息(例如名称，优先级，线程组等)列出或显示到有关此线程组的标准输出。
     *
     */
    @Test
    public void TestThreadGroup2() throws InterruptedException {
        ThreadGroup myGroup = new ThreadGroup("myGroup");
        //获取当前线程的group
        Thread thread1 = new Thread(myGroup,new Runnable() {
            @Override
            public void run() {
                System.out.println("thread1");
            }
        },"thread1");

        Thread thread2 = new Thread(myGroup,new Runnable() {
            @Override
            public void run() {
                System.out.println("thread2");
            }
        },"thread2");

        thread1.start();
        thread2.start();
        //用于将信息(例如名称，优先级，线程组等)列出或显示到有关此线程组的标准输出。
        myGroup.list();
        Thread.sleep(100);
        //线程组中存活的线程数量，睡100ms 线程就终止了
        System.out.println(myGroup.activeCount());
        /**
         * 使用场景
         * 要查找某个用户的最近2个月的通话记录，起 2个子线程，分别查找最近2个月的记录，然后通过
         */
        while (myGroup.activeCount() > 0 ) {
            System.out.println("activeCount=" + myGroup.activeCount() );
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 线程内部 无法抛出异常
     */
    @Test
    public void testSleep() throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {

                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("B" + i);
            }
        });
        thread.start();
        thread.join();

    }

    /**
     * Thread.yield(); 让出cpu 回到就绪状态
     */
    @Test
    public void testYield() throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("A"+i );
                if(i % 10 == 0)
                    Thread.yield();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("-------B" +i);
                if(i % 10 == 0)
                    Thread.yield();
            }
        });
        thread.start();
        thread2.start();
        thread.join();
        thread2.join();

    }

    /**
     *   将其他的线程加入到当前线程。
     *   等待其他线程的结束。
     */
    @Test
    public void testJoin() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("123");
            }
        });
        thread.start();
        thread.join();
    }
    @Test
    public void testState() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("123");
            }
        });
        System.out.println(thread.getState());

        thread.start();
        System.out.println(thread.getState());
        thread.join();
        System.out.println(thread.getState());
    }
}
