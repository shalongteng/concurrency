package com.slt.concurrency.book1.ch5.simplefuture;

/**
 * 返回 Data对象 ，立即返回FutureData ，并开启 ClientThread线程装RealData
 */
public class Client {
    public Data request(final String queryStr) {
        final FutureData futureData = new FutureData();
        // RealData的构建很慢
        new Thread() {
            public void run() {
                RealData realdata = new RealData(queryStr);
                futureData.setRealData(realdata);
            }
        }.start();
        return futureData;
    }
}
