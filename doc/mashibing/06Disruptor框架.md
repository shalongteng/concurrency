#1、 介绍
    主页：http://lmax-exchange.github.io/disruptor/
    源码：https://github.com/LMAX-Exchange/disruptor
    GettingStarted: https://github.com/LMAX-Exchange/disruptor/wiki/Getting-Started
    api: http://lmax-exchange.github.io/disruptor/docs/index.html
    maven: https://mvnrepository.com/artifact/com.lmax/disruptor
    
    Disruptor 是一个Java的并发编程框架，简化了并发程序开发的难度，性能上也比Java本身提供的一些并发包要好。
    单机最快的MQ，内存里存放元素的一个高效队列。
#2、 Disruptor的特点
    无锁，高并发，使用环形Buffer，直接覆盖（不用清除）旧的数据，降低GC频率。
    实现了基于事件的生产者消费者模式（观察者模式）
    
    生产者消费者容器：
        blockingQueue
        
    对比ConcurrentLinkedQueue : 链表实现，JDK中没有ConcurrentArrayQueue
        Disruptor是数组实现的

#3、Disruptor核心 -> RingBuffer 环形队列
    维护了一个序号（sequence），指向下一个可用的元素
    采用数组实现，没有首尾指针。
    
    对比ConcurrentLinkedQueue，用数组实现的速度更快
    > 假如长度为8，当添加到第12个元素的时候在哪个序号上呢？用12%8决定  
    > 当Buffer被填满的时候到底是覆盖还是等待，由Producer决定
    > 长度设为2的n次幂，利于二进制计算，例如：12%8 = 12 & (8 - 1)  pos = num & (size -1)
    
    8中等待策略
        blocking wait
#4、 Disruptor开发步骤
    消息就是 event事件
    1. 定义Event - 队列中需要处理的元素
    2. 定义Event工厂，用于填充队列
       > 这里牵扯到效率问题：disruptor初始化的时候，会调用Event工厂，对ringBuffer进行内存的提前分配
       > 直接覆盖旧数据，GC产频率会降低
    3. 定义EventHandler（消费者），处理容器中的元素
    
#5、事件发布
    1、普通事件发布模板
        //生产者部分代码
        long sequence = ringBuffer.next();  // Grab the next sequence
        try {
            LongEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
            // for the sequence
            event.set(8888L);  // Fill with data
        } finally {
            ringBuffer.publish(sequence);
        }

    2、使用EventTranslator发布事件
        //生产者部分代码
        EventTranslator<LongEvent> translator1 = new EventTranslator<LongEvent>() {
            @Override
            public void translateTo(LongEvent event, long sequence) {
                event.set(8888L);
            }
        };
        ringBuffer.publishEvent(translator1);

    3、 使用Lamda表达式
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        ringBuffer.publishEvent((event, sequence) -> event.set(10000L));
        System.in.read();

#6、 ProducerType生产者线程模式
    > ProducerType有两种模式 
        Producer.MULTI
        Producer.SINGLE
    > 默认是MULTI，表示在多线程模式下产生sequence
    > 如果确认是单线程生产者，那么可以指定SINGLE，效率会提升
    > 如果是多个生产者（多线程），但模式指定为SINGLE，会出什么问题呢？
        单线程不会加锁，生产者会覆盖。

#7、 等待策略
    1，(常用）BlockingWaitStrategy：通过线程阻塞的方式，等待生产者唤醒，被唤醒后，再循环检查依赖的sequence是否已经消费。
    
    2，BusySpinWaitStrategy：线程一直自旋等待，可能比较耗cpu
    
    3，LiteBlockingWaitStrategy：线程阻塞等待生产者唤醒，与BlockingWaitStrategy相比，
        区别在signalNeeded.getAndSet,如果两个线程同时访问一个访问waitfor,一个访问signalAll时，
        可以减少lock加锁次数.
    
    4，LiteTimeoutBlockingWaitStrategy：与LiteBlockingWaitStrategy相比，设置了阻塞时间，超过时间后抛异常。
    
    5，PhasedBackoffWaitStrategy：根据时间参数和传入的等待策略来决定使用哪种等待策略
    
    6，TimeoutBlockingWaitStrategy：相对于BlockingWaitStrategy来说，设置了等待时间，超过后抛异常
    
    7，（常用）YieldingWaitStrategy：尝试100次，然后Thread.yield()让出cpu
    
    8. （常用）SleepingWaitStrategy : sleep

#8、 消费者异常处理
    默认：disruptor.setDefaultExceptionHandler()
    
    覆盖：disruptor.handleExceptionFor().with()

## 依赖处理

