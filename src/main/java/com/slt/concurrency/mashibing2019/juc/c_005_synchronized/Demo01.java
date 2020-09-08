/**
 * ����һ�������������
 * @author mashibing
 */

package com.slt.concurrency.mashibing2019.juc.c_005_synchronized;

/**
 * main �߳��˳��������߳�Ҳ�����ִ���ꡣ
 */
public class Demo01 implements Runnable {

	private /*volatile*/ int count = 10000;

	@Override
	public /*synchronized*/ void run() {
		count--;
		System.out.println(Thread.currentThread().getName() + " count = " + count);
	}

	public static void main(String[] args) throws InterruptedException {
		Demo01 t = new Demo01();
		for(int i=0; i<10000; i++) {
			Thread thread = new Thread(t, "THREAD" + i);
			thread.start();
			//join��һ���߳��Ժ�main����ִ�У���Ҫ�ȴ���һ��ִ����ϲ���ִ�У�һ������ִ�еڶ��� ���Լ����д���
			//�൱�ڽ� 10000 ���̴߳��������ˡ�
//			thread.join();
		}

	}

}
