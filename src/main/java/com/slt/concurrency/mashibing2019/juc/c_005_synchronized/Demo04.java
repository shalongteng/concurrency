/**
 * 一个同步方法可以调用另外一个同步方法，一个线程已经拥有某个对象的锁，再次申请的时候仍然会得到该对象的锁.
 * 也就是说synchronized获得的锁是可重入的
 * @author mashibing
 */
package com.slt.concurrency.mashibing2019.juc.c_009;

import java.util.concurrent.TimeUnit;

/**
 * synchronized
 * 可重入
 */
public class Demo04 {
	synchronized void m1() {
		System.out.println("m1 start");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		m2();
		System.out.println("m1 end");
	}

	synchronized void m2() {
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("m2");
	}

	public static void main(String[] args) {
		/**
		 * main 线程调用m1 时候需要拿到对象的锁
		 * m1 中调用 m2
		 * synchronized 可重入
		 */
		new Demo04().m1();
	}
}
