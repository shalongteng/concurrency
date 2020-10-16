#1、JMH  Java Microbenchmark Harness
    1、什么是JMH
        Java准测试工具套件
    2、测试环境
        官网推荐将代码打成jar放到空的服务器上运行来测试
        实际运用多数都是在开发环境的测试，在IDE中测试。
#2、官网
    http://openjdk.java.net/projects/code-tools/jmh/ 
#3、创建JMH测试
    1. 创建Maven项目，添加依赖
        <dependencies>
           <!-- https://mvnrepository.com/artifact/org.openjdk.jmh/jmh-core -->
           <dependency>
               <groupId>org.openjdk.jmh</groupId>
               <artifactId>jmh-core</artifactId>
               <version>1.21</version>
           </dependency>
        
           <!-- https://mvnrepository.com/artifact/org.openjdk.jmh/jmh-generator-annprocess -->
           <dependency>
               <groupId>org.openjdk.jmh</groupId>
               <artifactId>jmh-generator-annprocess</artifactId>
               <version>1.21</version>
               <scope>test</scope>
           </dependency>
        </dependencies>
    
    2. idea安装JMH插件 JMH plugin v1.0.3
    
    3. 由于用到了注解，打开运行程序注解配置
       > compiler -> Annotation Processors -> Enable Annotation Processing
    
    4. 定义需要测试类PS (ParallelStream)
    
    5. 写单元测试
       > 这个测试类一定要在test package下面
     
    6. 运行测试类，如果遇到下面的错误：
       Exception while trying to acquire the JMH lock (C:\WINDOWS\/jmh.lock): C:\WINDOWS\jmh.lock (拒绝访问。)
       这个错误是因为JMH运行需要访问系统的TMP目录，解决办法是：
       打开RunConfiguration -> Environment Variables -> include system environment viables
    
    7. 阅读测试报告

#4、 JMH中的基本概念
    1. @Warmup(iterations = 10, time = 3)
       预热，由于JVM中对于特定代码会存在优化（本地化），预热对于测试结果很重要
       虚拟机起来 预热十次，每次预热3秒
       
    2. @Measurement(iterations = 1, time = 3)
       总共执行多少次测试，间隔三秒
    3. Timeout
       
    4. @Fork(5)
       Threads线程数，
       
    5. @BenchmarkMode(Mode.Throughput) 
       Throughput 吞吐量
       基准测试的模式
    6. Benchmark
       测试哪一段代码

#5、 Next
    官方样例：
    http://hg.openjdk.java.net/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/

