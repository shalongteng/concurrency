package com.slt.concurrency.book1.ch5.simplefuture;

/**
 * Future数据构造很快，但是是一个虚拟的数据， 需要装配Rea!Data
 * FutureData 是 Future 模式的关键。它实 际上是真实数据 RealData 的代理，
 */
public class FutureData implements Data {
    protected RealData realdata = null;
    protected boolean isReady = false;

    public synchronized void setRealData(RealData realdata) {
        if (isReady) {
            return;
        }
        this.realdata = realdata;
        isReady = true;
        notifyAll();
    }
    @Override
    public synchronized String getResult() {
        while (!isReady) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        return realdata.result;
    }
}
