/**
 * synchronized关键字
 * 对某个对象加锁
 * @author mashibing
 */

package com.slt.concurrency.mashibing2019.juc.c_001_Synchronized;

/**
 * synchronized 对某个对象加锁
 */
public class Synchronized01 {

	private int count = 10;
	private Object o = new Object();

	public void m() {
		//任何线程要执行下面的代码，必须先拿到o的锁
		synchronized(o) {
			count--;
			System.out.println(Thread.currentThread().getName() + " count = " + count);
		}
	}

}

