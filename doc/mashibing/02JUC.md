#0、限流
     一般使用guava  ratelimiter
#1、LongAdder
    LongAdder 在并发特别多时候，有优势
        分段锁
    
#2、同步工具
    CountDownLatch
    
    CyclicBarrier
        使用举例：
            复杂操作：
                网络
                数据库
                读文件
            并发执行：
                线程-操作
                线程-操作
                等三个线程一起做完操作以后。
    Semaphore
        限流
        限制同时执行数量。
        
    Exchanger   
        线程之间交换数据使用
        exchange 时候会阻塞当前线程
    phaser
        阶段
        遗传算法
        分段的一个一个的栅栏
    
#3、LOCK
    读写锁
        共享锁
        排它锁
#4、phaser
