/**
 * reentrantlock�������synchronized
 * ����������m1����this,ֻ��m1ִ����ϵ�ʱ��,m2����ִ��
 * �����Ǹ�ϰsynchronized��ԭʼ������
 * @author mashibing
 */
package com.slt.concurrency.mashibing2019.juc.c_020_LockAndTools;

import java.util.concurrent.TimeUnit;
/**
 * synchronized Ҳ�ǿ�����
 */
public class T01_ReentrantLock1 {
	synchronized void m1() {
		for(int i=0; i<10; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(i);
			if(i == 2) m2();
		}

	}

	synchronized void m2() {
		System.out.println("m2 ...");
	}

	/**
	 * ��д�Ĺ���  �����ڲ���->����������lambda���ʽ��->��������
	 * @param args
	 */
	public static void main(String[] args) {
		T01_ReentrantLock1 rl = new T01_ReentrantLock1();

		new Thread(rl::m1).start();

//		new Thread(()->{
//			rl.m1();
//		}).start();

//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				rl.m1();
//			}
//		}).start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//new Thread(rl::m2).start();
	}
}
