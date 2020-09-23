package com.slt.concurrency.book1.ch5.pstream.demo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Plus implements Runnable {
	public static BlockingQueue<Msg> blockingQueue =new LinkedBlockingQueue<Msg>();
	@Override
	public void run() {
		while(true){
			try {
				Msg msg= blockingQueue.take();
				msg.j=msg.i+msg.j;
				Multiply.bq.add(msg);
			} catch (InterruptedException e) {
			}
		}
	}
}
