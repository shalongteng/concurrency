package com.slt.concurrency.book1.chapter1;

/**
 * 对于 32 位系统来说， long 型数据的读写不是原子性的（因为 long 型数据有 64 位〕 。 也就是说 ，如果
 * 两个线程同时对 long 型数据进行写入（或者读取〉 ， 则对线程之间的结果是有干扰的 。
 *
 * 64位虚拟机不会有输出
 */
public class MultiThreadLong {
    public static long t = 0;

    public static class ChangeT implements Runnable {
        private long to;

        public ChangeT(long to) {
            this.to = to;
        }

        @Override
        public void run() {
            while (true) {
                MultiThreadLong.t = to;
                Thread.yield();
            }
        }
    }

    public static class ReadT implements Runnable {
        @Override
        public void run() {
            while (true) {
                long temp = MultiThreadLong.t;
                if (temp != 111L && temp != -999L && temp != 333L && temp != -444L) {
                    System.out.println(temp);
                }
                Thread.yield();
            }
        }
    }

    public static void main(String args[]) {
        new Thread(new ChangeT(111L)).start();
        new Thread(new ChangeT(-999L)).start();
        new Thread(new ChangeT(333L)).start();
        new Thread(new ChangeT(-444L)).start();
        new Thread(new ReadT()).start();
    }
}
