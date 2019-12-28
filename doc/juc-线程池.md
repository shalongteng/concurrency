#前言   https://www.jianshu.com/p/7ab4ae9443b9
可以利用多线程来最大化地压榨CPU多核计算的能力。但是，线程本身是把双刃剑，我们需要知道它的利弊，才能在实际系统中游刃有余地运用。

#线程池的基本概念。

线程池，本质上是一种对象池，用于管理线程资源。
在任务执行前，需要从线程池中拿出线程来执行。
在任务执行完成之后，需要把线程放回线程池。

#线程池优点。

降低资源的消耗。创建和销毁线程会有CPU开销；创建的线程也会占用一定的内存。
提高任务执行的响应速度。任务执行时，可以不必等到线程创建完之后再执行。
提高线程的可管理性。线程不能无限制地创建，需要进行统一的分配、调优和监控。

#任务提交之后是怎么执行的。大致如下：

判断核心线程池是否已满，如果不是，则创建线程执行任务
如果核心线程池满了，判断队列是否满了，如果队列没满，将任务放在队列中
如果队列满了，则判断线程池是否已满，如果没满，创建线程执行任务
如果线程池也满了，则按照拒绝策略对任务进行处理

corePool -> 核心线程池
maximumPool -> 线程池
BlockQueue -> 队列
RejectedExecutionHandler -> 拒绝策略
#Executors
Executors是一个线程池工厂，提供了很多的工厂方法，
```java
public class Executors {
    
// 创建单一线程的线程池
public static ExecutorService newSingleThreadExecutor();
// 创建固定数量的线程池
public static ExecutorService newFixedThreadPool(int nThreads);
// 创建带缓存的线程池
public static ExecutorService newCachedThreadPool();
// 创建定时调度的线程池
public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize);
// 创建流式（fork-join）线程池
public static ExecutorService newWorkStealingPool();
}
```
1. 创建单一线程的线程池
顾名思义，这个线程池只有一个线程。若多个任务被提交到此线程池，那么会被缓存到队列（队列长度为Integer.MAX_VALUE）。
当线程空闲的时候，按照FIFO的方式进行处理。

2. 创建固定数量的线程池
和创建单一线程的线程池类似，只是这儿可以并行处理任务的线程数更多一些罢了。若多个任务被提交到此线程池，会有下面的处理过程。

如果线程的数量未达到指定数量，则创建线程来执行任务
如果线程池的数量达到了指定数量，并且有线程是空闲的，则取出空闲线程执行任务
如果没有线程是空闲的，则将任务缓存到队列（队列长度为Integer.MAX_VALUE）。当线程空闲的时候，按照FIFO的方式进行处理

3. 创建带缓存的线程池
这种方式创建的线程池，核心线程池的长度为0，线程池最大长度为Integer.MAX_VALUE。由于本身使用SynchronousQueue作为等待队列的缘故，
导致往队列里面每插入一个元素，必须等待另一个线程从这个队列删除一个元素。

4. 创建定时调度的线程池
和上面3个工厂方法返回的线程池类型有所不同，它返回的是ScheduledThreadPoolExecutor类型的线程池。平时我们实现定时调度功能的时候，
可能更多的是使用第三方类库，比如：quartz等。但是对于更底层的功能，我们仍然需要了解。

#手动创建线程池
理论上，我们可以通过Executors来创建线程池，这种方式非常简单。但正是因为简单，所以限制了线程池的功能。比如：无长度限制的队列，可能因为任务堆积导致OOM，这是非常严重的bug，应尽可能地避免。怎么避免？归根结底，还是需要我们通过更底层的方式来创建线程池。

抛开定时调度的线程池不管，我们看看ThreadPoolExecutor。它提供了好几个构造方法，但是最底层的构造方法却只有一个。那么，我们就从这个构造方法着手分析。

```java
public class ThreadPoolExecutor extends AbstractExecutorService {
    
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler);
}
```
这个构造方法有7个参数，我们逐一来进行分析。

corePoolSize，线程池中的核心线程数
maximumPoolSize，线程池中的最大线程数
keepAliveTime，空闲时间，当线程池数量超过核心线程数时，多余的空闲线程存活的时间，即：这些线程多久被销毁。
unit，空闲时间的单位，可以是毫秒、秒、分钟、小时和天，等等
workQueue，等待队列，线程池中的线程数超过核心线程数时，任务将放在等待队列，它是一个BlockingQueue类型的对象
threadFactory，线程工厂，我们可以使用它来创建一个线程
handler，拒绝策略，当线程池和等待队列都满了之后，需要通过该对象的回调函数进行回调处理

这些参数里面，基本类型的参数都比较简单，我们不做进一步的分析。我们更关心的是workQueue、threadFactory和handler，
##1. 等待队列-workQueue

  等待队列是BlockingQueue类型的，理论上只要是它的子类，我们都可以用来作为等待队列。
  
  同时，jdk内部自带一些阻塞队列，我们来看看大概有哪些。
  
  ArrayBlockingQueue，队列是有界的，基于数组实现的阻塞队列
  LinkedBlockingQueue，队列可以有界，也可以无界。基于链表实现的阻塞队列
  SynchronousQueue，不存储元素的阻塞队列，每个插入操作必须等到另一个线程调用移除操作，否则插入操作将一直处于阻塞状态。
  该队列也是Executors.newCachedThreadPool()的默认队列
  
  PriorityBlockingQueue，带优先级的无界阻塞队列
  通常情况下，我们需要指定阻塞队列的上界（比如1024）。另外，如果执行的任务很多，我们可能需要将任务进行分类，然后将不同分类的任务放到不同的线程池中执行。
  
##2. 线程工厂-threadFactory
ThreadFactory是一个接口，只有一个方法。既然是线程工厂，那么我们就可以用它生产一个线程对象。来看看这个接口的定义。
##3. 拒绝策略-handler
  所谓拒绝策略，就是当线程池满了、队列也满了的时候，我们对任务采取的措施。或者丢弃、或者执行、或者其他...
  
  jdk自带4种拒绝策略，我们来看看。
  
  CallerRunsPolicy // 在调用者线程执行
  AbortPolicy // 直接抛出RejectedExecutionException异常
  DiscardPolicy // 任务直接丢弃，不做任何处理
  DiscardOldestPolicy // 丢弃队列里最旧的那个任务，再尝试执行当前任务
  这四种策略各有优劣，比较常用的是DiscardPolicy，但是这种策略有一个弊端就是任务执行的轨迹不会被记录下来。所以，我们往往需要实现自定义的拒绝策略， 通过实现RejectedExecutionHandler接口的方式。
  
#提交任务的几种方式
 往线程池中提交任务，主要有两种方法，execute()和submit()。
 
 execute()用于提交不需要返回结果的任务，我们看一个例子。
 
 submit()用于提交一个需要返回果的任务。该方法返回一个Future对象，通过调用这个对象的get()方法，我们就能获得返回结果。
 get()方法会一直阻塞，直到返回结果返回。另外，我们也可以使用它的重载方法get(long timeout, TimeUnit unit)，
 这个方法也会阻塞，但是在超时时间内仍然没有返回结果时，将抛出异常TimeoutException。
 
#关闭线程池
 在线程池使用完成之后，我们需要对线程池中的资源进行释放操作，这就涉及到关闭功能。我们可以调用线程池对象的shutdown()
 和shutdownNow()方法来关闭线程池。
 
 这两个方法都是关闭操作，又有什么不同呢？
 
 shutdown()会将线程池状态置为SHUTDOWN，不再接受新的任务，同时会等待线程池中已有的任务执行完成再结束。
 
 shutdownNow()会将线程池状态置为SHUTDOWN，对所有线程执行interrupt()操作，清空队列，并将队列中的任务返回回来。
 另外，关闭线程池涉及到两个返回boolean的方法，isShutdown()和isTerminated，分别表示是否关闭和是否终止。
 
 #线程池监控
  如果系统中大量用到了线程池，那么我们有必要对线程池进行监控。利用监控，我们能在问题出现前提前感知到，
  也可以根据监控信息来定位可能出现的问题。
  
  首先，ThreadPoolExecutor自带了一些方法。
  
  long getTaskCount()，获取已经执行或正在执行的任务数
  long getCompletedTaskCount()，获取已经执行的任务数
  int getLargestPoolSize()，获取线程池曾经创建过的最大线程数，根据这个参数，我们可以知道线程池是否满过
  int getPoolSize()，获取线程池线程数
  int getActiveCount()，获取活跃线程数（正在执行任务的线程数）
  其次，ThreadPoolExecutor留给我们自行处理的方法有3个，它在ThreadPoolExecutor中为空实现（也就是什么都不做）。
  
  protected void beforeExecute(Thread t, Runnable r) // 任务执行前被调用
  protected void afterExecute(Runnable r, Throwable t) // 任务执行后被调用
  protected void terminated() // 线程池结束后被调用
  针对这3个方法，我们写一个例子。


#继承图

#Timer

#juc框架图 写五个md
tools locks aotmic collections executor