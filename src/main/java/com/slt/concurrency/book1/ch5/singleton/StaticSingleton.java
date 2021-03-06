package com.slt.concurrency.book1.ch5.singleton;

public class StaticSingleton {
	public static int STATUS;
	private StaticSingleton(){
		System.out.println("StaticSingleton is create");
	}
	private static class SingletonHolder {
		private static StaticSingleton instance = new StaticSingleton();
	}
	public static StaticSingleton getInstance() {
		return SingletonHolder.instance;
	}
}

