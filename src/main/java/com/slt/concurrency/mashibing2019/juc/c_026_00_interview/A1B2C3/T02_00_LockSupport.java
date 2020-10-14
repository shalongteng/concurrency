package com.slt.concurrency.mashibing2019.juc.c_026_00_interview.A1B2C3;


import java.util.concurrent.locks.LockSupport;

//Locksupport park 当前线程阻塞（停止）
//unpark(Thread t)

public class T02_00_LockSupport {
    static Thread t1 = null, t2 = null;

    public static void main(String[] args) throws Exception {
        char[] aI = "1234567".toCharArray();
        char[] aC = "ABCDEFG".toCharArray();

        t1 = new Thread(() -> {
            for(char c : aI) {
                LockSupport.park(); //T1阻塞 数字先阻塞
                System.out.print(c);
                LockSupport.unpark(t2); //叫醒T2
            }

        }, "t1");

        t2 = new Thread(() -> {
            for(char c : aC) {
                System.out.print(c);
                LockSupport.unpark(t1); //叫醒t1
                LockSupport.park(); //t2阻塞
            }

        }, "t2");

        t1.start();
        t2.start();
    }
}


