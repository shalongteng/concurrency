package com.slt.concurrency.mashibing2019.juc.c_005_synchronized; /**
 * һ��ͬ���������Ե�������һ��ͬ��������һ���߳��Ѿ�ӵ��ĳ������������ٴ������ʱ����Ȼ��õ��ö������.
 * Ҳ����˵synchronized��õ����ǿ������
 * �����Ǽ̳����п��ܷ��������Σ�������ø����ͬ������
 * @author mashibing
 */

import java.util.concurrent.TimeUnit;
/**
 * Ϊʲôsynchronized��������룿
 *
 */
public class Demo05 {
	synchronized void m() {
		System.out.println("m start");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("m end");
	}

	public static void main(String[] args) {
		new TT().m();
	}

}

/**
 * ����̳и��� ��д�����synchronized������
 * ����super.m(); ����������룬ֱ������
 */
class TT extends Demo05 {
	@Override
	synchronized void m() {
		System.out.println("child m start");
		super.m();
		System.out.println("child m end");
	}
}
