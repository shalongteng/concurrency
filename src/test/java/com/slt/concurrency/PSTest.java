package com.slt.concurrency;

import com.slt.concurrency.mashibing2019.jmh.PS;
import org.openjdk.jmh.annotations.*;

/**
 * 属于测试开发的 高端知识
 * 测试要写到测试包里
 * 右键->run
 */
public class PSTest {
    @Benchmark
    @Warmup(iterations = 1, time = 3)
    @Fork(5)
    @BenchmarkMode(Mode.Throughput)
    @Measurement(iterations = 1, time = 3)
    public void testForEach() {
        PS.foreach();
    }
}
