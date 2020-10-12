#0、限流
     一般使用guava  ratelimiter
#1、LongAdder
    LongAdder 在并发特别多时候，有优势
        分段锁
    
#2、同步工具
    CountDownLatch
        CountDown:倒数
        Latch:门栓
        
        latch.countDown();
        latch.await();
        
    CyclicBarrier 循环栅栏
        new CyclicBarrier(20);
        barrier.await();
        
        使用举例：
            复杂操作分三步： 
                网络读数据
                数据库读数据
                读文件
                
            顺序执行：效率较低
            并发执行：
                线程-操作
                线程-操作
                等三个线程一起做完操作以后。
    phaser
        分阶段执行
        
        遗传算法
        分段的一个一个的栅栏 CyclicBarrier
        
        //注册了七个线程
        phaser.bulkRegister(7);
        phaser.arriveAndAwaitAdvance();
        //部分线程解注册
        phaser.arriveAndDeregister();
         
         
    Semaphore 信号量
        s.acquire(); 取得 //获取许可 获得锁
        s.release();
        
        限流
        限制【同时】执行数量。
        
    Exchanger   交换器
        线程之间交换数据使用
        exchange 时候会阻塞当前线程
        
        exchanger.exchange(s);
    
#3、LOCK
    Conditoin 条件
        与Lock配合可以实现等待/通知模式
        Condition producer = lock.newCondition();  生成一个等待队列  
        Condition consumer = lock.newCondition();  一个condition就是一个等待队列
        
        producer.await();
        consumer.signalAll();
    
    ReentrantLock 可重入锁
        底层基于CAS
        
        与synchronized的区别：
            tryLock()
            lockInterruptibly(); //可被中断
            new ReentrantLock(true)//公平锁
            
    readWriteLock:读写锁  适合读多写少的场景
        读锁 共享锁 readWriteLock.readLock();
        写锁 排它锁 readWriteLock.writeLock();
#4、LockSuppor t 锁支持
    LockSupport.park(); 
        park 停车 当前线程停止，阻塞了
    LockSupport.unpark(t); 
        叫醒某个指定的线程，继续运行
#5、AQS（CLH）
    volatile int state
        state由他的子类来实现
        ReentrantLock中 用它表示加锁和解锁 0表示无锁 >1表示有锁
    
    AQS中 有一个Node双向链表 队列，每一个node存的是线程（Thread）    
    
    维护了一个队列的同步器框架
        
    varHandle
        普通属性也可进行原子操作。
        比反射快，直接操纵二进制码。
#6、ThreadLocal
    set方法
        set(ThreadLocal,value)
        ThreadLocalMap map = getMap(t);
        map.set(this, value);
        设置到了当前线程的map
    get方法
         ThreadLocalMap map = getMap(currentThread);
         if (map != null) {
            ThreadLocalMap.Entry e = map.getEntry(this);
            if (e != null) {
                T result = (T)e.value;
                return result;
            }，。
         }
    ThreadLocal使用：spring声明式事务
        把connection 放到线程ThreadLocal里，以后在拿connection时候不从线程池中拿。
        需要保证是同一个connection 对象才能保证事务。
#7、强软弱须
    StrongReference
        
    SoftReference
        如果内存空间不足了，就会回收这些对象的内存。
        
        软引用的作用：软引用可用来实现内存敏感的高速缓存。
        
    WeakReference
        垃圾回收时候，不管当前内存空间足够与否，都会回收它的内存。
        
        一般用在容器里
        
    PhantomReference
        如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收器回收。
       
        虚引用主要用来跟踪对象被垃圾回收器回收的活动。虚引用与软引用和弱引用的一个区别在于：
        虚引用必须和引用队列（ReferenceQueue）联合使用。
        
        管理堆外内存