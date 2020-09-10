package com.slt.concurrency.book1.ch5.disruptor;

import java.nio.ByteBuffer;

import com.lmax.disruptor.RingBuffer;

public class Producer {
    //环形缓冲区
    private final RingBuffer<PCData> ringBuffer;

    public Producer(RingBuffer<PCData> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * pushData（）方法的功能就是将传入的ByteBuffer中的数据提取出来，
     * 并装载到环形缓冲区中。
     * @param byteBuffer
     */
    public void pushData(ByteBuffer byteBuffer) {
        long sequence = ringBuffer.next();  //得到下一个可用的序列号
        try {
            PCData pcData = ringBuffer.get(sequence); // 通过序列号，取得下一个空闲可用 的 PCData 对象
            // for the sequence
            pcData.set(byteBuffer.getLong(0));  // Fill with data
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
