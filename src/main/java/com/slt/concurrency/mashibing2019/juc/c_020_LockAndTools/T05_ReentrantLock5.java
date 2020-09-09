/**
 * reentrantlock�������synchronized
 * ����m1����this,ֻ��m1ִ����ϵ�ʱ��,m2����ִ��
 * �����Ǹ�ϰsynchronized��ԭʼ������
 *
 * ʹ��reentrantlock�������ͬ���Ĺ���
 * ��Ҫע����ǣ�����Ҫ����Ҫ����Ҫ�ֶ��ͷ�������Ҫ������˵���飩
 * ʹ��syn�����Ļ���������쳣��jvm���Զ��ͷ���������lock�����ֶ��ͷ�������˾�����finally�н��������ͷ�
 *
 * ʹ��reentrantlock���Խ��С�����������tryLock�������޷�������������ָ��ʱ�����޷��������߳̿��Ծ����Ƿ�����ȴ�
 *
 * ʹ��ReentrantLock�����Ե���lockInterruptibly���������Զ��߳�interrupt����������Ӧ��
 * ��һ���̵߳ȴ����Ĺ����У����Ա����
 *
 * ReentrantLock������ָ��Ϊ��ƽ��
 *
 * @author mashibing
 */
package com.slt.concurrency.mashibing2019.juc.c_020_LockAndTools;

import java.util.concurrent.locks.ReentrantLock;

/**
 * new ReentrantLock(true) 公平锁
 */
public class T05_ReentrantLock5 extends Thread {

	private static ReentrantLock lock=new ReentrantLock(true);
    public void run() {
        for(int i=0; i<100; i++) {
            lock.lock();
            try{
                System.out.println(Thread.currentThread().getName()+"线程");
            }finally{
                lock.unlock();
            }
        }
    }
    public static void main(String[] args) {
        T05_ReentrantLock5 rl=new T05_ReentrantLock5();
        Thread th1=new Thread(rl);
        Thread th2=new Thread(rl);
        th1.start();
        th2.start();
    }
}
