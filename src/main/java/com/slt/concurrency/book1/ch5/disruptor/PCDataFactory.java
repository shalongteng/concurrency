package com.slt.concurrency.book1.ch5.disruptor;

import com.lmax.disruptor.EventFactory;

public class PCDataFactory implements EventFactory<PCData> {
    public PCData newInstance() {
        return new PCData();
    }
}
