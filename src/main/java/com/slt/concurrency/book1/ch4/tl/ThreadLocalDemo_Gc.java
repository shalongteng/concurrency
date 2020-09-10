
package com.slt.concurrency.book1.ch4.tl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadLocal 的变量，我们也手动将其设置为 null，比如 tl=null ， 那么
 * 这个 ThreadLocal 对应的所有线程的局部变量都有可能被回收
 */
public class ThreadLocalDemo_Gc {
    static volatile ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>() {
        protected void finalize() throws Throwable {
            System.out.println(this.toString() + " is gc");
        }
    };
    static volatile CountDownLatch countDownLatch = new CountDownLatch(10000);

    public static class ParseDate implements Runnable {
        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        public void run() {
            try {
                if (threadLocal.get() == null) {
                    threadLocal.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") {
                        protected void finalize() throws Throwable {
                            System.out.println(this.toString() + " is gc");
                        }
                    });
                    System.out.println(Thread.currentThread().getId() + ":create SimpleDateFormat");
                }
                Date t = threadLocal.get().parse("2015-03-29 19:29:" + i % 60);
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10000; i++) {
            es.execute(new ParseDate(i));
        }
        /**
         * 因为线程池 是固定的10个 所以 中端输出的 只有十个线程号
         * 因为同一个线程第二次时候，不符合if条件 if (threadLocal.get() == null)
         */
        countDownLatch.await();
        System.out.println("mission complete!!");
        threadLocal = null;
        System.gc();
        System.out.println("first GC complete!!");
        //在设置ThreadLocal的时候，会清除ThreadLocalMap中的无效对象
        threadLocal = new ThreadLocal<SimpleDateFormat>();
        countDownLatch = new CountDownLatch(10000);
        for (int i = 0; i < 10000; i++) {
            es.execute(new ParseDate(i));
        }
        countDownLatch.await();
        Thread.sleep(1000);

        System.gc();
        System.out.println("second GC complete!!");

    }
}
