package com.slt.concurrency.mashibing2019.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * 事件 工厂
 */
public class LongEventFactory implements EventFactory<LongEvent> {

    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
