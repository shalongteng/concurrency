/**
 * �Ա�����һ��С���򣬷���һ�������������
 * @author mashibing
 */

package com.slt.concurrency.mashibing2019.juc.c_005_synchronized;

/**
 * synchronized
 */
public class Demo02 implements Runnable {

	private int count = 10;

	/**
	 * ��Ҫ�õ� this
	 */
	public synchronized  void run() {
		count--;
		System.out.println(Thread.currentThread().getName() + " count = " + count);
	}
	/**
	 * T t = new T();
	 * ����ŵ�forѭ����ߣ���������ͬһ�����󣬶�ͬһ�����������
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
