package com.slt.concurrency.book1.ch5.disruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * Disruptor 框架的性能要比
 * BlockingQueue 队列至少 高一个数量级 以上
 */
public class PCMain {
    public static void main(String[] args) throws Exception {
        Executor executor = Executors.newCachedThreadPool();
        PCDataFactory factory = new PCDataFactory();
        // 设置缓冲 区大小为 1024
        int bufferSize = 1024;
        Disruptor<PCData> disruptor = new Disruptor<PCData>(
                factory,
                bufferSize,
                executor,
                ProducerType.MULTI,
                new BlockingWaitStrategy()
        );
        // Connect the handler
//        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.handleEventsWithWorkerPool(
                new Consumer(),
                new Consumer(),
                new Consumer(),
                new Consumer());
        disruptor.start();

        RingBuffer<PCData> ringBuffer = disruptor.getRingBuffer();
        Producer producer = new Producer(ringBuffer);
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        for (long i = 0; true; i++) {
            byteBuffer.putLong(0, i);
            producer.pushData(byteBuffer);
            Thread.sleep(100);
            System.out.println("add data " + i);
        }
    }
}
