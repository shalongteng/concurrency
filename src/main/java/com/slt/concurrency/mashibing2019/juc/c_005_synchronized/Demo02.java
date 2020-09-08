/**
 * 对比上面一个小程序，分析一下这个程序的输出
 * @author mashibing
 */

package com.slt.concurrency.mashibing2019.juc.c_005_synchronized;

/**
 * synchronized
 */
public class Demo02 implements Runnable {

	private int count = 10;

	/**
	 * 需要拿到 this
	 */
	public synchronized  void run() {
		count--;
		System.out.println(Thread.currentThread().getName() + " count = " + count);
	}
	/**
	 * T t = new T();
	 * 必须放到for循环外边，这样才是同一个对象，对同一个对象加锁。
	 */
	public static void main(String[] args) {
		Demo02 t = new Demo02();
		for(int i=0; i<5; i++) {
//			T t = new T();
			new Thread(t, "THREAD " + i).start();
		}
		new Thread(new Demo02(),"666").start();
	}

}
