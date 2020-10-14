/**
 * ��ʶCallable����Runnable��������չ
 * ��Callable�ĵ��ã������з���ֵ
 */
package com.slt.concurrency.mashibing2019.juc.c_026_01_ThreadPool;

import java.util.concurrent.*;

public class T03_Callable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> callable = new Callable() {
            @Override
            public String call() throws Exception {
                return "Hello Callable";
            }
        };

        ExecutorService service = Executors.newCachedThreadPool();
        Future<String> future = service.submit(callable); //�첽

        System.out.println(future.get());//����.0

        service.shutdown();
    }

}
