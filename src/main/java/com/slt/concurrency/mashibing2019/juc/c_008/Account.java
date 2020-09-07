/**
 * �����⣺ģ�������˻�
 * ��ҵ��д��������
 * ��ҵ�������������
 * �����в��У�
 *
 * ���ײ���������⣨dirtyRead��
 */

package com.slt.concurrency.mashibing2019.juc.c_008;

import java.util.concurrent.TimeUnit;

/**
 * �����⣺ģ�������˻�
 * 	��ҵ��д��������
 * 	��ҵ�������������
 * 	�����в��У�
 *
 * 	���ײ���������⡣
 * 	ԭ��
 * 	synchronized ���Ժͷ� synchronized һ�����С�
 */
public class Account {
	String name;
	double balance;
	
	public synchronized void set(String name, double balance) {
		this.name = name;

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		
		this.balance = balance;
	}
	
	public /*synchronized*/ double getBalance(String name) {
		return this.balance;
	}
	
	
	public static void main(String[] args) {
		Account a = new Account();
		new Thread(()->a.set("zhangsan", 100.0)).start();
//		new Thread(a::set("lisi",98.0)).start();

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(a.getBalance("zhangsan"));
		
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(a.getBalance("zhangsan"));
	}
}
