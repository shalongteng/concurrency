package com.slt.concurrency.book1.ch6.completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 完成普通future的工作
 *
 * thenCompose
 *
 * @author Geym
 *
 */
public class CFutureMain5 {

    public static Integer calc(Integer para) {
        return para/2;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture<Void> fu =
                CompletableFuture.supplyAsync(() -> calc(50))
                .thenCompose((i)->CompletableFuture.supplyAsync(() -> calc(i)))
                .thenApply((str)->"\"" + str + "\"").thenAccept(System.out::println);
        fu.get();
    }

}
