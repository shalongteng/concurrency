#前言   https://blog.csdn.net/shenxm1966/article/details/53894715
可以利用多线程来最大化地压榨CPU多核计算的能力。

#线程池的基本概念。

线程池，本质上是一种对象池，用于管理线程资源。
在任务执行前，需要从线程池中拿出线程来执行。
在任务执行完成之后，需要把线程放回线程池。

#execute方法主要三个步骤：
活动线程小于corePoolSize的时候创建新的线程；
活动线程大于corePoolSize时都是先加入到任务队列当中；
任务队列满了再去启动新的线程，如果线程数达到最大值就拒绝任务

#Executors方法创建自带线程池
线程池不建议使用Executors去创建，而是通过ThreadPoolExecutor的方式，这样的处理方式让写的同学更加明确线程池的运行规则，
规避资源耗尽的风险。 说明：Executors各个方法的弊端：
1）newFixedThreadPool和newSingleThreadExecutor:
  主要问题是堆积的请求处理队列可能会耗费非常大的内存，甚至OOM。
2）newCachedThreadPool和newScheduledThreadPool:
  主要问题是线程数最大数是Integer.MAX_VALUE，可能会创建数量非常多的线程，甚至OOM。

ThreadPoolExecutor的构造方法如下：
```java
public class ThreadPoolExecutor extends AbstractExecutorService {
    
public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler){}
}
```

corePoolSize：核心线程池大小，活动线程小于corePoolSize则直接创建，大于等于则先加到workQueue中，队列满了才创建新的线程。

maximumPoolSize： 线程池最大线程数，它表示在线程池中最多能创建多少个线程；

keepAliveTime： 线程从队列中获取任务的超时时间，也就是说如果线程空闲超过这个时间就会终止。

unit： 参数keepAliveTime的时间单位，有7种取值，在TimeUnit类中有7种静态属性：

workQueue： 一个阻塞队列，用来存储等待执行的任务。 一般来说，这里的阻塞队列有以下几种选择：
``` 
    ArrayBlockingQueue;  
    LinkedBlockingQueue;  
    SynchronousQueue
```


threadFactory： 线程工厂，主要用来创建线程；
handler： 表示当拒绝处理任务时的策略，有以下四种取值：

ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
ThreadPoolExecutor.CallerRunsPolicy：只要线程池不关闭，该策略直接在调用者线程中，运行当前被丢弃的任务

个人认为这4中策略不友好，最好自己定义拒绝策略，实现RejectedExecutionHandler接口

#2. Java里的阻塞队列
JDK7提供了7个阻塞队列。分别是

ArrayBlockingQueue ：一个由数组结构组成的有界阻塞队列。
LinkedBlockingQueue ：一个由链表结构组成的有界阻塞队列。
PriorityBlockingQueue ：一个支持优先级排序的无界阻塞队列。
DelayQueue：一个使用优先级队列实现的无界阻塞队列。
SynchronousQueue：一个不存储元素的阻塞队列。
LinkedTransferQueue：一个由链表结构组成的无界阻塞队列。
LinkedBlockingDeque：一个由链表结构组成的双向阻塞队列。
LinkedBlockingQueue是一个用链表实现的有界阻塞队列。此队列的默认和最大长度为Integer.MAX_VALUE。此队列按照先进先出的原则对元素进行排序。


#juc继承图

#Timer

