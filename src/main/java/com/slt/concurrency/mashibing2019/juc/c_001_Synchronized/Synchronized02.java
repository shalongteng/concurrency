/**
 * synchronized关键字
 * 对某个对象加锁
 * @author mashibing
 */

package com.slt.concurrency.mashibing2019.juc.c_001_Synchronized;

/**
 * synchronized
 * 对某个对象加锁
 */
public class Synchronized02 {

	private int count = 10;

	public void m() {
		/**
		 * 这样写可以缩小锁的粒度
		 * 任何线程要执行下面的代码，必须先拿到this的锁
		 */
		synchronized(this) {
			count--;
			System.out.println(Thread.currentThread().getName() + " count = " + count);
		}
	}

}

