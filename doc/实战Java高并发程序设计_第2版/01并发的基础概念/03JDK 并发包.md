#1、同步控制
    1.关键字 synchronized 的功能扩展：重入锁
       重入锁还提供了一些高级功能
       1. 中断晌应    lockInterruptibly
       2. 锁申请等待限时  tryLock
       3. 公平锁  new ReentrantLock(true);
       
       重入锁的实现：
        原子状态 。 原子状态使用 CAS 操作来存储当前锁的状态，判断锁是否己经被别的线程持有了 。
        等待队列 。 没有请求到锁的线程，会进入等待队列进行等待。
        阻塞原语 。 park（）和 unpark（），用来挂起和恢复线程。没有得到锁的线程将会被挂起。
             
    2、Condition
        newCondition（）方法可以生成一个与当前重入锁绑定的Condition 实例
    3、信号量（Semaphore)
        信号量却可 以指定多个线程，同时访问某一个资源。
    4、ReadWritelock 读写锁    
        · 读－读不互斥 ： 读读之间不阻塞 。
        · 读－写互斥 ： 读阻塞写 ， 写也会阻塞读。
        · 写－写互斥 ： 写写阻塞 。
    5、倒计数器： CountDownlatch
        countDown();
        end()
    6、循环栅栏： CyclicBarrier
        Cyclic 意为循环，也就是说这个计数器可以反复使用。
    7、线程阻塞工具类： LockSupport
        LockSupport.park(this); 挂起线程
        LockSupport.unpark(t1);
    8、Guava 和 Ratelimiter ~限流
        一般化的限流算法有两种：
            漏桶算法
                 利用一个缓存区，当有请求进入系统时，无论请求的速率如何，都先在缓存区内保存，
                 然后以固定的流速流出缓存区进行处理，
            令牌桶算法。
                在令牌桶算法中，桶中存放的不再是请求，而是令牌。处理程序只有拿到令牌后，才能对请求进行处理。
                如果没有令牌，那么处理程序要么丢弃请求，要么等待可用的令牌 。 
                
        RateLimiter 正是采用了令牌桶算法
        
#2、线程池
    1、JDK对线程池的支持
        Executors提供的线程池
            public static ExecutorService newFixedThreadPool(int nThreads)
                该方法返回一个固定线程数量的线程池 。
            public static ExecutorService newSingleThreadExecutor ()
                该方法返回 一个只有一个线程的线程池。
            public static ExecutorService newCachedThreadPool ()
                该方法返回 一个可根据实际情况调整线程数量的线程 池
            public static ScheduledExecutorService newSingleThreadScheduledExecutor ()
                该方法返回一个 ScheduledExecutorSenrice对象，线程池大小为 1
            public stat 工C ScheduledExecutorService newScheduledThreadPool(int corePoolSize)
                
        
    2、核心线程池的内部实现
        ThreadPoo!Executor 
            • corePoolSize ：    指定了线程池中的线程数量。
            • maximumPoolSize ： 指定了线程池中的最大线程数量。
            • keepAliveTime ：   当线程池线程数量超过 corePoolSize 时， 多余的空闲钱程的存活时
                间，即超过 corePoolSize 的空闲钱程，在多长时间内会被销毁 。
            • unit: keepAliveTime 的单位。
            • workQueue ： 任务队列，被提交但尚未被执行的任务 。
                直接提交的队列：SynchronousQueue
                    提交的任务不会被真实地保存，而总是将新任务提交给线程执行，如果没有空闲的线程，则尝试创建新的线程，
                    如果线程数量己经达到最大值，则执行拒绝策略。因此，使用 SynchronousQueue 队列，通常要设置很大的
                    maximumPoolSize 值，否则很容易执行拒绝策略。
                    
                有界的任务队列：可以使用 ArrayBlockingQueue 
                    当使用有界的任务队列时， 若有新的任务需要执行，如果线程池的实际线程数小于corePoolSize ，优先创建新的线程，
                    若大于 corePoolSize ，则会将新任务加入等待队列 。 若等待队列己满，则在总线程数不大于 maximumPoolSize 的前
                    提下 ，创建新的进程执行任务。若大于 maximumPoolSize，则执行拒绝策略。可见，
                    有界队列仅当在任务队列装满肘 ， 才可能将线程数提升到 corePoolSize 以上，
                无界的任务队列 ： LinkedBlockingQueue
                    当有新的任务到来，线程数小于 corePoolSize 时 ，线程池会生成新的线程执行任务，当线程数达到 corePoolSize 后，
                    就不会继续增加了。若后续仍有新 的任务加入 ，而又没有空闲的线程资源， 则任务直接进入队列等待 。
                    若任务创建和处理的速度差异很大，无界队列会保持快速增长，直到耗尽系统内存 。
                优先任务队列 ： PriorityBlockingQueue
                    可 以控制任务的执行先后顺序 。 它是一个特殊的无界队列
            • threadFactory ： 线程工厂，用于创建线程 ， 一般用默认的即可 。
            • handler ： 拒绝策略。当任务太多来不及处理时，如何拒绝任务 。
                
    3、拒绝策略
        JDK 内置四种拒绝策略：
            • AbortPolicy 策略：该策略会直接抛出异常，阻止系统正常工作 。
            • CallerRunsPolicy 策略：只要线程池未关闭，该策略直接在调用者线程中，运行当前
                被丢弃的任务 。 显然这样做不会真的丢弃任务，但是，任务提交线程的性能极有可
                能会急剧下降 。
            • DiscardO!destPolicy 策略 ： 该策略将丢弃最老的一个请求，也就是即将被执行的一个
                任务，并尝试再次提交当前任务。
            • DiscardPolicy 策略 ： 该策略默默地丢弃无法处理的任务，不予任何处理。如果允许
                任务丢失，我觉得这可能是最好的一种方案了吧！
    4、自定义线程创建： ThreadFactory
        使用自定义线程池可以让我们更加自由地设置线程池中所有线程的状态。
    5、扩展线程池
        ThreadPoolExecutor 是一个可以扩展的线程池。它提供了三个接口用来对线程池进行控制
            beforeExecuteO 、
            afterExecute（）
            terminated。 
    6、优化线程池线程数量
        Nthreads = Ncpu× Ucpu×(1 + W/C) 
    7、分而治之： Fork/Join 框架
        在JDK 中 ， 给出了一个 ForkJoinPool 线程池， 
        public <T> ForkJo工nTask<T > subrnit(ForkJoinTask<T> task)
    7、guava 对线程池的扩展
       1、DirectExecutor
       1、Daemon 线程池
        
       1、对 Future 模式的扩展
#3、JDK 的并发容器
    1、线程安全的 HashMap
        Collections.synchronizedMap()
        ConcurrentHashMap
    1、List 的线程安全
        Vector
        Collections. synchronizedList（）
    1、高效读写的队列 ：ConcurrentlinkedQueue 类
    
    1、高效读取：不变模式下的 CopyOnWriteArraylist 类
        所谓 CopyOnWrite 就是在写入操作时，进行一次 自我复制    
    5、数据共享通道： BlockingQueue
        多个线程间的数据共享呢？比如，线程 A 希望给线程 B 发一条消息，用什么方式告知线程 B 是比较合理的呢 ？
    6、数据共享通道： BlockingQueue
        BlockingQueue 之所以适合作为数据共享的通道
        
        ArrayBlockingQueue 类和 
            基于数组实现的，更适合做有界队列
        LinkedBlockingQueue ，无界队列   
    7、随机数据结构：跳表（ Skiplist)
        ConcurrentSkipListMap
        对跳表的插入和删除只需要对整个数据结构的局部进行操作即可 。
        在高井发的情况下，你会需要一个全局锁来保证整个平衡树的线程安全。而对于跳表，你只 需要部分锁即可 。
#4、使用 JMH 进行性能测试
