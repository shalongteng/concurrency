/**
 * synchronized关键字
 * 对某个对象加锁
 * @author mashibing
 */

package com.slt.concurrency.mashibing2019.juc.c_001_Synchronized;

public class Synchronized04 {

	private static int count = 10;

	//这里等同于synchronized(T.class)
	public synchronized static void m() {
		count--;
		System.out.println(Thread.currentThread().getName() + " count = " + count);
	}

	public static void mm() {
		synchronized(Synchronized04.class) { //考虑一下这里写synchronized(this)是否可以？
			count --;
		}
	}

}
