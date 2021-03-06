/**
 * reentrantlock用于替代synchronized
 * 本例中由于m1锁定this,只有m1执行完毕的时候,m2才能执行
 * 这里是复习synchronized最原始的语义
 * @author mashibing
 */
package com.slt.concurrency.mashibing2019.juc.c_020_LockAndTools;

import java.util.concurrent.TimeUnit;
/**
 * synchronized 也是可重入
 */
public class T01_ReentrantLock1 {
	synchronized void m1() {
		for(int i=0; i<10; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(i);
			if(i == 2) m2();
		}

	}

	synchronized void m2() {
		System.out.println("m2 ...");
	}

	/**
	 * 简写的过程  匿名内部类->匿名方法（lambda表达式）->方法引用
	 * @param args
	 */
	public static void main(String[] args) {
		T01_ReentrantLock1 rl = new T01_ReentrantLock1();

		new Thread(rl::m1).start();

//		new Thread(()->{
//			rl.m1();
//		}).start();

//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				rl.m1();
//			}
//		}).start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//new Thread(rl::m2).start();
	}
}
