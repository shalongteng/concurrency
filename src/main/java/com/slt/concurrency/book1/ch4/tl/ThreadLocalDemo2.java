package com.slt.concurrency.book1.ch4.tl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用 ThreadLocal 为每一个线程创造一个 SimpleDateformat 对象实例。
 */
public class ThreadLocalDemo2 {
    static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();

    public static class ParseDate implements Runnable {
        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        public void run() {
            try {
				/**
				 * 如果当前线程不持有 SimpleDateformat 对象实例，那么就新
				 * 建一个并把它设置到当前线程中 ，如果己经持有，则直接使用 。
				 */
				if (threadLocal.get() == null) {
					//会使用当前线程的threadlocal 赋值给 Threadlocal变量
                    threadLocal.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                }
                Date t = threadLocal.get().parse("2015-03-29 19:29:" + i % 60);
                System.out.println(i + ":" + t);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            es.execute(new ParseDate(i));
        }
    }
}
