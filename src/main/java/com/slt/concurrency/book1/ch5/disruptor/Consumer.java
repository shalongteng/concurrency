package com.slt.concurrency.book1.ch5.disruptor;

import com.lmax.disruptor.WorkHandler;

public class Consumer implements WorkHandler<PCData> {
	/**
	 * onEvent为框架的回调方法 。
	 */
    @Override
    public void onEvent(PCData pcData) throws Exception {
        System.out.println(Thread.currentThread().getId() + ":pcData: --"
                + pcData.get() * pcData.get() + "--");
    }
}
