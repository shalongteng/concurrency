package com.slt.concurrency.mashibing2019.juc.c_005_synchronized; /**
 * 一个同步方法可以调用另外一个同步方法，一个线程已经拥有某个对象的锁，再次申请的时候仍然会得到该对象的锁.
 * 也就是说synchronized获得的锁是可重入的
 * 这里是继承中有可能发生的情形，子类调用父类的同步方法
 * @author mashibing
 */

import java.util.concurrent.TimeUnit;
/**
 * 为什么synchronized必须可重入？
 *
 */
public class Demo05 {
	synchronized void m() {
		System.out.println("m start");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("m end");
	}

	public static void main(String[] args) {
		new TT().m();
	}

}

/**
 * 子类继承父类 重写父类的synchronized方法。
 * 调用super.m(); 如果不可重入，直接死锁
 */
class TT extends Demo05 {
	@Override
	synchronized void m() {
		System.out.println("child m start");
		super.m();
		System.out.println("child m end");
	}
}
