package com.slt.concurrency.myTest.jdk.Semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {

	private static final int THREAD_COUNT = 30;

	private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);

	private static Semaphore s = new Semaphore(10);
	/**
	 * @Description: 在代码中，尽管有30个线程在执行，但是只允许10个并发的执行。
	 * @Author: shalongteng
	 * @Date: 2019-12-08
	 */
	public static void main(String[] args) {
		for (int i = 0; i < THREAD_COUNT; i++) {
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						s.acquire();
						System.out.println("save data");
						System.out.println(Thread.currentThread().getName());
						Thread.sleep(10000);
						s.release();
					} catch (InterruptedException e) {
					}
				}
			});
		}

		threadPool.shutdown();
	}
}