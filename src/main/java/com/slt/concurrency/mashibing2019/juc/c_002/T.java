/**
 * synchronized�ؼ���
 * ��ĳ���������
 * @author mashibing
 */

package com.slt.concurrency.mashibing2019.juc.c_002;

/**
 * synchronized
 * ��ĳ���������
 */
public class T {
	
	private int count = 10;
	
	public void m() {
		//�κ��߳�Ҫִ������Ĵ��룬�������õ�this����
		synchronized(this) {
			count--;
			System.out.println(Thread.currentThread().getName() + " count = " + count);
		}
	}
	
}

