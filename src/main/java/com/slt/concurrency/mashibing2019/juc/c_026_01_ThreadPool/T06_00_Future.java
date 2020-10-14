/**
 * ��ʶfuture
 * �첽
 */
package com.slt.concurrency.mashibing2019.juc.c_026_01_ThreadPool;

import java.util.concurrent.*;

public class T06_00_Future {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		/**
		 * ����һ������
		 * ����һ��future
		 */
		FutureTask<Integer> task = new FutureTask<>(()->{
			TimeUnit.MILLISECONDS.sleep(500);
			return 1000;
		}); //new Callable () { Integer call();}

		new Thread(task).start();
		System.out.println(task.get()); //����


		ExecutorService executorService = Executors.newFixedThreadPool(5);
		Future<Integer> future = executorService.submit(() -> {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return 1;
		});

		System.out.println(future.get());
		System.out.println(future.isDone());
	}
}
