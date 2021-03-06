package com.slt.concurrency.mashibing2019.juc.c_026_01_ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class T07_SingleThreadPool {
	public static void main(String[] args) {
		//Executors 线程池的工厂
		ExecutorService service = Executors.newSingleThreadExecutor();
		for(int i=0; i<5; i++) {
			final int j = i;
			service.execute(()->{
				System.out.println(j + " " + Thread.currentThread().getName());
			});
		}

	}
}
