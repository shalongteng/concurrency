/**
 * synchronized�ؼ���
 * ��ĳ���������
 * @author mashibing
 */

package com.slt.concurrency.mashibing2019.juc.c_003;

public class T {

	private int count = 10;
	//��ͬ���ڷ����Ĵ���ִ��ʱҪsynchronized(this)
	public synchronized void m() {
		count--;
		System.out.println(Thread.currentThread().getName() + " count = " + count);
	}


}
