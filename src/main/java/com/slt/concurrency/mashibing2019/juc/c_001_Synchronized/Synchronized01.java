/**
 * synchronized�ؼ���
 * ��ĳ���������
 * @author mashibing
 */

package com.slt.concurrency.mashibing2019.juc.c_001_Synchronized;

/**
 * synchronized ��ĳ���������
 */
public class Synchronized01 {

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

