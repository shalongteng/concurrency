/**
 * synchronized�ؼ���
 * ��ĳ���������
 * @author mashibing
 */

package com.slt.concurrency.mashibing2019.juc.c_001_Synchronized;

public class Synchronized04 {

	private static int count = 10;

	//�����ͬ��synchronized(T.class)
	public synchronized static void m() {
		count--;
		System.out.println(Thread.currentThread().getName() + " count = " + count);
	}

	public static void mm() {
		synchronized(Synchronized04.class) { //����һ������дsynchronized(this)�Ƿ���ԣ�
			count --;
		}
	}

}
