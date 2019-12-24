#AQS->AbstractQueuedSynchronizer
抽象的队列式的同步器


#countdownlatch

#semaphore

#cyclicbarrier
栅栏类似于闭锁，它能阻塞一组线程直到某个事件的发生。栅栏与闭锁的关键区别在于，所有的线程必须全部到达栅栏位置，
才能继续执行。【闭锁用于等待事件，而栅栏用于等待其他线程】。

CyclicBarrier可以使一定数量的线程反复地在栅栏位置处汇集。当线程到达栅栏位置时将调用await方法，
这个方法将阻塞直到所有线程都到达栅栏位置。如果所有线程都到达栅栏位置，那么栅栏将打开，此时所有的线程都将被释放，
而栅栏将被重置以便下次使用

实现原理：在CyclicBarrier的内部定义了一个Lock对象，每当一个线程调用await方法时，
将拦截的线程数减1，然后判断剩余拦截数是否为初始值parties，如果不是，进入Lock对象的条件队列等待。
如果是，执行barrierAction对象的Runnable方法，然后将锁的条件队列中的所有线程放入锁等待队列中，
这些线程会依次的获取锁、释放锁。

#reentrantlock

#condition

#futuretask