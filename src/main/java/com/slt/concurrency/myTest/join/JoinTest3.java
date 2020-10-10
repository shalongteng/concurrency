package com.slt.concurrency.myTest.join;
/**
 * 网址：https://www.iteye.com/blog/uule-1101994
 * @Auther: shalongteng
 * @Date: 2019-12-08
 * @Description:
 */
class CustomThread1 extends Thread {

    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " start.");
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(threadName + " loop at " + i);
                Thread.sleep(1000);
            }
            System.out.println(threadName + " end.");
        } catch (Exception e) {
            System.out.println("Exception from " + threadName + ".run");
        }
    }
}

class CustomThread extends Thread {
    CustomThread1 t1;
    public CustomThread(CustomThread1 t1) {
        this.t1 = t1;
    }
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + " start.");
        try {
            t1.join();
            System.out.println(threadName + " end.");
        } catch (Exception e) {
            System.out.println("Exception from " + threadName + ".run");
        }
    }
}

public class JoinTest3 {
    /**
     * @Description: main start.    //main方法所在的线程起动，但没有马上结束，
     * 因为调用t.join();，所以要等到t结束了，此线程才能向下执行。
     *
     * @Author: shalongteng
     * @Date: 2019-12-08
     */
//    public static void main(String[] args) {
//        System.out.println(Thread.currentThread().getName() + " start.");
//        CustomThread1 t1 = new CustomThread1();
//        CustomThread t = new CustomThread(t1);
//        try {
//            t1.start();
//            Thread.sleep(2000);
//            t.start();
//            t.join(); //在代碼2里，將此處注釋掉
//        } catch (Exception e) {
//            System.out.println("Exception from main");
//        }
//        System.out.println(Thread.currentThread().getName() + " end!");
//    }

    /**
     * @Description: main 线程睡两秒以后就结束了，但是必须所有线程退出，程序才会退出
     * @Param:
     * @return:
     * @Author: shalongteng
     * @Date: 2019-12-08
     */
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " start.");
        CustomThread1 t1 = new CustomThread1();
        CustomThread t = new CustomThread(t1);
        try {
            t1.start();
            Thread.sleep(2000);
            t.start();
//            t.join(); //在代碼2里，將此處注釋掉
        } catch (Exception e) {
            System.out.println("Exception from main");
        }
        System.out.println(Thread.currentThread().getName() + " end!");
    }


    /**
     * @Description:   main 线程调用t.join时，必须能够拿到线程t对象的锁，
     * 如果拿不到它是无法wait的 ，刚开的例子t.join(1000)不是说明了main线程等待1 秒，
     * 如果在它等待之前，其他线程获取了t对象的锁，它等待时间可不就是1秒了
     */
}
