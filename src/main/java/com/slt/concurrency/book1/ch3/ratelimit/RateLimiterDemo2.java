package com.slt.concurrency.book1.ch3.ratelimit;

import com.google.common.util.concurrent.RateLimiter;

public class RateLimiterDemo2 {
	//limit仅 仅支持 1 秒两次调用。也就是每 500 毫秒可以产生一个令牌 ，for循环效率很高，完全可以在500ms内完成。
	//因此本段代码最终只产生一个输出，其余请求全部被丢弃。
	static RateLimiter limiter = RateLimiter.create(2);

	public static class Task implements Runnable {
		@Override
		public void run() {
			System.out.println(System.currentTimeMillis());
		}
	}

	public static void main(String args[]) throws InterruptedException {
		for (int i = 0; i < 50; i++) {
			if(!limiter.tryAcquire()) {
			    continue;
			}
			new Thread(new Task()).start();
		}
	}
}
