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
    ReentrantLock 可重入锁
        底层基于CAS
        
        与synchronized的区别：
            tryLock()
            lockInterruptibly(); //可被中断
            new ReentrantLock(true)//公平锁
            
    readWriteLock:读写锁  适合读多写少的场景
        读锁 共享锁 readWriteLock.readLock();
        写锁 排它锁 readWriteLock.writeLock();
