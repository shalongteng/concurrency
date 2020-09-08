/**
 * һ��ͬ���������Ե�������һ��ͬ��������һ���߳��Ѿ�ӵ��ĳ������������ٴ������ʱ����Ȼ��õ��ö������.
 * Ҳ����˵synchronized��õ����ǿ������
 * @author mashibing
 */
package com.slt.concurrency.mashibing2019.juc.c_009;

import java.util.concurrent.TimeUnit;

/**
 * synchronized
 * ������
 */
public class Demo04 {
	synchronized void m1() {
		System.out.println("m1 start");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		m2();
		System.out.println("m1 end");
	}

	synchronized void m2() {
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("m2");
	}

	public static void main(String[] args) {
		/**
		 * main �̵߳���m1 ʱ����Ҫ�õ��������
		 * m1 �е��� m2
		 * synchronized ������
		 */
		new Demo04().m1();
	}
}
