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
        
#2、同步控制
#2、同步控制
#2、同步控制
