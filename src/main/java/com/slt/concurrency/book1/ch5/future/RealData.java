package com.slt.concurrency.book1.ch5.future;

import java.util.concurrent.Callable;

public class RealData implements Callable<String> {
    private String para;
    public RealData(String para){
    	this.para=para;
    }

    /**
     *  call（）方法会构造我们需要的真实数据井返回
     */
	@Override
	public String call() throws Exception {

    	StringBuffer sb=new StringBuffer();
        for (int i = 0; i < 10; i++) {
        	sb.append(para);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        return sb.toString();
	}
}
