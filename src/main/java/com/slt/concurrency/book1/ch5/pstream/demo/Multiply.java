package com.slt.concurrency.book1.ch5.pstream.demo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Multiply implements Runnable {
	public static BlockingQueue<Msg> bq = new LinkedBlockingQueue<Msg>();

	@Override
	public void run() {
		while (true) {
			try {
				Msg msg = bq.take();
				msg.i = msg.i * msg.j;
				Div.bq.add(msg);
			} catch (InterruptedException e) {
			}
		}
	}
}
