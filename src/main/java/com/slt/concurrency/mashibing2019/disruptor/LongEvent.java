package com.slt.concurrency.mashibing2019.disruptor;

/**
 * 消息事件  生产者
 */
public class LongEvent
{
    private long value;

    public void set(long value)
    {
        this.value = value;
    }

    @Override
    public String toString() {
        return "LongEvent{" +
                "value=" + value +
                '}';
    }
}
