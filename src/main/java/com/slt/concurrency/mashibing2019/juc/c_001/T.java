/**
 * synchronized�ؼ���
 * ��ĳ���������
 * @author mashibing
 */

package com.slt.concurrency.mashibing2019.juc.c_001;

/**
 * synchronized ��ĳ���������
 */
public class T {
	
	private int count = 10;
	private Object o = new Object();
	
	public void m() {
		//�κ��߳�Ҫִ������Ĵ��룬�������õ�o����
		synchronized(o) {
			count--;
			System.out.println(Thread.currentThread().getName() + " count = " + count);
		}
	}
	
}

