/**
 * 分析一下这个程序的输出
 * @author mashibing
 */

package com.slt.concurrency.mashibing2019.juc.c_005;

/**
 *
 */
public class T implements Runnable {

	private /*volatile*/ int count = 10000;

	@Override
	public /*synchronized*/ void run() {
		count--;
		System.out.println(Thread.currentThread().getName() + " count = " + count);
	}

	public static void main(String[] args) throws InterruptedException {
		T t = new T();
		for(int i=0; i<10000; i++) {
			Thread thread = new Thread(t, "THREAD" + i);
			thread.start();
			thread.join();
		}
	}

}
