package com.slt.concurrency.mashibing2019.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

public class Main02 {
    public static void main(String[] args) throws Exception {
        LongEventFactory factory = new LongEventFactory();
        int bufferSize = 1024;
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(new LongEventHandler());
        disruptor.start();

        // 生产者部分代码1  为Java8的写法做准备
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        EventTranslator<LongEvent> translator1 = new EventTranslator<LongEvent>() {
            @Override
            public void translateTo(LongEvent event, long sequence) {
                event.set(8888L);
            }
        };
        ringBuffer.publishEvent(translator1);

        //生产者部分代码2  为Java8的写法做准备
        EventTranslatorOneArg<LongEvent, Long> translator2 = new EventTranslatorOneArg<LongEvent, Long>() {
            @Override
            public void translateTo(LongEvent event, long sequence, Long l) {
                event.set(l);
            }
        };
        ringBuffer.publishEvent(translator2, 7777L);

        //生产者部分代码3  为Java8的写法做准备
        EventTranslatorTwoArg<LongEvent, Long, Long> translator3 = new EventTranslatorTwoArg<LongEvent, Long, Long>() {
            @Override
            public void translateTo(LongEvent event, long sequence, Long l1, Long l2) {
                event.set(l1 + l2);
            }
        };
        ringBuffer.publishEvent(translator3, 10000L, 10000L);

        //生产者部分代码4  为Java8的写法做准备
        EventTranslatorThreeArg<LongEvent, Long, Long, Long> translator4 = new EventTranslatorThreeArg<LongEvent, Long, Long, Long>() {
            @Override
            public void translateTo(LongEvent event, long sequence, Long l1, Long l2, Long l3) {
                event.set(l1 + l2 + l3);
            }
        };
        ringBuffer.publishEvent(translator4, 10000L, 10000L, 1000L);

        //生产者部分代码5  为Java8的写法做准备
        EventTranslatorVararg<LongEvent> translator5 = new EventTranslatorVararg<LongEvent>() {
            @Override
            public void translateTo(LongEvent event, long sequence, Object... objects) {
                long result = 0;
                for (Object o : objects) {
                    long l = (Long) o;
                    result += l;
                }
                event.set(result);
            }
        };
        ringBuffer.publishEvent(translator5, 10000L, 10000L, 10000L, 10000L);

    }
}
