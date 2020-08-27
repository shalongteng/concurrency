package com.slt.concurrency.book1.chapter2;

/**
 * Created by 13 on 2017/5/4.
 */
public class JoinMain {
    public volatile static int i = 0;

    public static class AddThread extends Thread {
        public void run() {
            System.out.println("add!");
            for (i = 0; i < 1000000; i++) ;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) throws InterruptedException {

        AddThread at = new AddThread();
        at.start();
        at.join();//
        //
        System.out.println(i);
    }
}
