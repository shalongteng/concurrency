/**
 * synchronized�ؼ���
 * ��ĳ���������
 * @author mashibing
 */

package com.slt.concurrency.mashibing2019.juc.c_001_Synchronized;

/**
 * synchronized
 * ��ĳ���������
 */
public class Synchronized02 {

	private int count = 10;

	public void m() {
		/**
		 * ����д������С��������
		 * �κ��߳�Ҫִ������Ĵ��룬�������õ�this����
		 */
		synchronized(this) {
			count--;
			System.out.println(Thread.currentThread().getName() + " count = " + count);
		}
	}

}

