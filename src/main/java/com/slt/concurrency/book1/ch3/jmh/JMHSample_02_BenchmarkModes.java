//package com.slt.concurrency.book1.ch3.jmh;
//
//import org.openjdk.jmh.annotations.Benchmark;
//import org.openjdk.jmh.annotations.BenchmarkMode;
//import org.openjdk.jmh.annotations.Mode;
//import org.openjdk.jmh.annotations.OutputTimeUnit;
//import org.openjdk.jmh.runner.Runner;
//import org.openjdk.jmh.runner.RunnerException;
//import org.openjdk.jmh.runner.options.Options;
//import org.openjdk.jmh.runner.options.OptionsBuilder;
//import java.util.concurrent.TimeUnit;
//
//public class JMHSample_02_BenchmarkModes {
//
//    /**
//     * 测试吞吐量
//     */
//    @Benchmark
//    @BenchmarkMode(Mode.Throughput)
//    @OutputTimeUnit(TimeUnit.SECONDS)
//    public void measureThroughput() throws InterruptedException {
//        TimeUnit.MILLISECONDS.sleep(100);
//    }
//
//    /**
//     * 统计方法的平均执行时间的设置如下 ：
//     */
//    @Benchmark
//    @BenchmarkMode(Mode.AverageTime)
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    public void measureAvgTime() throws InterruptedException {
//        TimeUnit.MILLISECONDS.sleep(100);
//    }
//
//    /**
//     * 通过采样得到部分方法的执行时间，：
//     */
//    @Benchmark
//    @BenchmarkMode(Mode.SampleTime)
//    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    public void measureSamples() throws InterruptedException {
//        TimeUnit.MILLISECONDS.sleep(100);
//    }
//
//    /*
//     */
//    @Benchmark
//    @BenchmarkMode(Mode.SingleShotTime)
//    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    public void measureSingleShot() throws InterruptedException {
//        TimeUnit.MILLISECONDS.sleep(100);
//    }
//
//    /*
//     * We can also ask for multiple benchmark modes at once. All the tests above can
//     * be replaced with just a single test like this:
//     */
//    @Benchmark
//    @BenchmarkMode({ Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime })
//    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    public void measureMultiple() throws InterruptedException {
//        TimeUnit.MILLISECONDS.sleep(100);
//    }
//
//    /*
//     * Or even...
//     */
//
//    @Benchmark
//    @BenchmarkMode(Mode.All)
//    @OutputTimeUnit(TimeUnit.MICROSECONDS)
//    public void measureAll() throws InterruptedException {
//        TimeUnit.MILLISECONDS.sleep(100);
//    }
//
//    /*
//     */
//    public static void main(String[] args) throws RunnerException {
//        Options opt = new OptionsBuilder().include(JMHSample_02_BenchmarkModes.class.getSimpleName()).forks(1).build();
//
//        new Runner(opt).run();
//    }
//
//}
