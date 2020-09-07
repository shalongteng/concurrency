/**
 * �Ա�����һ��С���򣬷���һ�������������
 * @author mashibing
 */

package com.slt.concurrency.mashibing2019.juc.c_006;

/**
 * synchronized
 */
public class T implements Runnable {

	private int count = 10;

	/**
	 * ��Ҫ�õ� this
	 */
	public /*synchronized */ void run() {
		synchronized(this){
			count--;
		}
		System.out.println(Thread.currentThread().getName() + " count = " + count);
	}

	public static void main(String[] args) {
		for(int i=0; i<5; i++) {
			T t = new T();
			new Thread(t, "THREAD" + i).start();
		}
	}

}
