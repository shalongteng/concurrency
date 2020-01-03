package com.slt.concurrency.example.blockingQueue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Classname ArrayBlockQueue
 * @Date 2020/1/3 9:30
 * @Created by slt
 * ____           _
 * / ___| __ ___   _(_)_ __
 * | |  _ / _` \ \ / / | '_ \
 * | |_| | (_| |\ V /| | | | |
 * \____|\__,_| \_/ |_|_| |_|
 */
public class ArrayBlockQueue {
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue queue=new ArrayBlockingQueue(10);
        for(int i=0;i<20;i++){
            queue.put("obj:"+i); //阻塞
            System.out.println("put "+i);
        }
    }
}
