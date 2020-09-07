package com.slt.concurrency.book1.ch3.ratelimit;

import com.google.common.util.concurrent.RateLimiter;

public class RateLimiterDemo {
	//限制了 RateLimiter 每秒只能处理两个请求
	static RateLimiter limiter = RateLimiter.create(2);

	public static class Task implements Runnable {
		@Override
		public void run() {
			System.out.println(System.currentTimeMillis());
		}
	}

	public static void main(String args[]) throws InterruptedException {
		for (int i = 0; i < 50; i++) {
			limiter.acquire();
			new Thread(new Task()).start();
		}
	}
}
