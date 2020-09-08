/**
 * ͬ���ͷ�ͬ�������Ƿ����ͬʱ���ã�
 * @author mashibing
 */

package com.slt.concurrency.mashibing2019.juc.c_005_synchronized;

/**
 * ͬ�������ͷ�ͬ�������Ƿ����ͬʱ���á�
 * ����
 */
public class Demo03 {

	public synchronized void m1() {
		System.out.println(Thread.currentThread().getName() + " m1 start...");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " m1 end");
	}

	public void m2() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " m2 ");
	}

	public static void main(String[] args) {
		Demo03 t = new Demo03();
		//lambda ���ʽ
		new Thread(()->t.m1(), "t1").start();
		new Thread(()->t.m2(), "t2").start();

		new Thread(t::m1, "t1").start();
		new Thread(t::m2, "t2").start();

		//1.8֮ǰ��д��  �����ڲ���
		new Thread(new Runnable() {

			@Override
			public void run() {
				t.m1();
			}

		}).start();

	}

}
