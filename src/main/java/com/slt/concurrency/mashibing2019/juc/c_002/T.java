/**
 * synchronized关键字
 * 对某个对象加锁
 * @author mashibing
 */

package com.slt.concurrency.mashibing2019.juc.c_002;

/**
 * synchronized
 * 对某个对象加锁
 */
public class T {
	
	private int count = 10;
	
	public void m() {
		//任何线程要执行下面的代码，必须先拿到this的锁
		synchronized(this) {
			count--;
			System.out.println(Thread.currentThread().getName() + " count = " + count);
		}
	}
	
}

