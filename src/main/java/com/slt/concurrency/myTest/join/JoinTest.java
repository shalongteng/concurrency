package com.slt.concurrency.myTest.join;

public class JoinTest implements Runnable{

    public static int a = 0;

    public void run() {
        for (int k = 0; k < 5; k++) {
            a = a + 1;
        }
    }
    /**
     * @Description: 当主线程 main方法执行System.out.println(a);这条语句时，线程还没有真正开始运行，
     * 或许正在为它分配资源准备运行。因为为线程分配资源需要时间，而main方法执行完t.start()方法后
     * 继续往下执行System.out.println(a);,这个时候得到的结果是a还没有被 改变的值0
     * @Param:
     * @return:
     * @Author: shalongteng
     * @Date: 2019-12-08
     */
//    public static void main(String[] args) throws Exception {
//        Runnable r = new JoinTest();
//        Thread t = new Thread(r);
//        t.start();
//        System.out.println(a);
//    }
    public static void main(String[] args) throws Exception {
        Runnable r = new JoinTest();
        Thread t = new Thread(r);
        t.start();
        //加入join() 阻塞main线程，就肯定能输出5
        t.join();
        System.out.println(a);
    }
}
