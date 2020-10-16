package com.slt.concurrency.mashibing2019.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

/**
 * lambda 表达式版本
 */
public class Main03 {
    public static void main(String[] args) throws Exception {
        int bufferSize = 1024;
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith((event, sequence, endOfBatch) -> System.out.println("Event: " + event));
        disruptor.start();

        // 生产者部分代码1  lambda表达式写法
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        ringBuffer.publishEvent((event, sequence) -> event.set(10000L));
        /**
         * 两个参数
         */
        ringBuffer.publishEvent((event, sequence, l) -> event.set(l), 10000L);
        ringBuffer.publishEvent((event, sequence, l1, l2) -> event.set(l1 + l2),10000L, 10000L);

        System.in.read();
    }
}
