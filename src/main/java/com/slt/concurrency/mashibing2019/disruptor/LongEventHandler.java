package com.slt.concurrency.mashibing2019.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * 消息的消费者
 */
public class LongEventHandler implements EventHandler<LongEvent> {

    public static long count = 0;

    /**
     * @param event
     * @param sequence RingBuffer的序号
     * @param endOfBatch 是否为最后一个元素
     * @throws Exception
     */
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        count ++;
        System.out.println("[" + Thread.currentThread().getName() + "]" + event + " 序号：" + sequence);
    }
}
