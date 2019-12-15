package com.slt.concurrency.example.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class AtomicExample4 {
 
    private static AtomicReference<Integer> count = new AtomicReference<>(0);
 
    public static void main(String[] args) {
        //和0 比较 成功返回true 并且更新成20
        count.compareAndSet(0, 20);
        //20 和 0 比较失败返回false  无法更新成1
        count.compareAndSet(0, 1); 
        log.info("count:{}", count.get());
    }
}
