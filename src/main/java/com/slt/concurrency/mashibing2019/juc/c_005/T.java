/**
 * 分析一下这个程序的输出
 * @author mashibing
 */

package com.slt.concurrency.mashibing2019.juc.c_005;

/**
 * main 线程退出后，其他线程也会继续执行完。
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
			//join第一个线程以后，main不能执行，需要等待第一个执行完毕才能执行，一次类推执行第二个 所以加这行代码
			//相当于将 10000 个线程串联起来了。
//			thread.join();
		}

	}

}
