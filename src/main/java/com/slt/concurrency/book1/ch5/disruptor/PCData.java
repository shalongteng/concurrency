package com.slt.concurrency.book1.ch5.disruptor;

public class PCData {
    private long value;

    public void set(long value) {
        this.value = value;
    }

    public long get() {
        return value;
    }
}
