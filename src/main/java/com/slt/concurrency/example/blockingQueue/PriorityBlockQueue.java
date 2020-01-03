package com.slt.concurrency.example.blockingQueue;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * @Classname PriorityBlockQueue
 * @Date 2020/1/3 10:27
 * @Created by Gavin
 * ____           _
 * / ___| __ ___   _(_)_ __
 * | |  _ / _` \ \ / / | '_ \
 * | |_| | (_| |\ V /| | | | |
 * \____|\__,_| \_/ |_|_| |_|
 */
public class PriorityBlockQueue {
    /**
     * 有优先级但是无界的阻塞队列，类似于List，支持自动扩容，可以指定初始化大小，也可以不指定。实际是一个Arr[]
     * 内部有一个最小堆，插入和取出的时候，都要构建堆有序
     * add()的时候不会报错，因为容量无限
     * 支持元素实现Compare接口，或者PriorityBlockingQueue初始化的时候传入一个Compare接口实现类，两种方式进行比较优先级
     */
    public static void main(String[] args) throws InterruptedException {
        PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<Task>(); //因为是无界队列，初始化可以不定义长度
        Task t1 = new Task();
        t1.setId(1);
        t1.setName("任务 1");

        Task t2 = new Task();
        t2.setId(4);
        t2.setName("任务 2");

        Task t3 = new Task();
        t3.setId(3);
        t3.setName("任务 3");


        queue.add(t2);
        queue.add(t3);
        queue.add(t1);

        Task take = queue.take();// 取出来最小的 任务2
        System.out.println(take.getName());
    }

    @Data
    @NoArgsConstructor
    static class Task implements Comparable{
        int id;
        String name;

        @Override
        public int compareTo(Object o) {
            if(o instanceof Task){

                return ((Task) o).id -id;
            }
            return 0;
        }
    }
}
