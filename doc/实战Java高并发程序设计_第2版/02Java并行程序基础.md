#1、进程，线程
    进程是系统进行【资源分配】和【调度】的基本单位
    线程就是轻量级进程，是程序执行的最小单位，线程间的切换和调度的成本远远小于进程 
#2、线程的状态
    Java语言定义了6种线程状态，
        新建（New）：创建后尚未启动的线程处于这种状态
        运行（Runnable）：包括操作系统线程状态中的Running和Ready，处于此状态的线程有可能正在执行，也可能正在等待着操作系统为它分配执行时间。
        无限期等待（Waiting）：处于这种状态的线程不会被分配处理器执行时间，它们要等待被其他线程显式唤醒。以下方法会让线程陷入无限期的等待状态：
            ■没有设置Timeout参数的Object::wait()方法；
            ■没有设置Timeout参数的Thread::join()方法；
            ■LockSupport::park()方法。
        限期等待（Timed Waiting）：这种状态的线程也不会被分配处理器执行时间，无须等待被其他线程显式唤醒，在一定时间之后它们会由系统自动唤醒。
            ■Thread::sleep()方法；
            ■设置了Timeout参数的Object::wait()方法；
            ■设置了Timeout参数的Thread::join()方法；
            ■LockSupport::parkNanos()方法；
            ■LockSupport::parkUntil()方法。
        阻塞（Blocked）：线程被阻塞了， 
            “阻塞状态”与“等待状态”的区别是“阻塞状态”在等待着获取到一个排它锁，这个事件将在另外一个线程放弃这个锁的时候发生；
            而“等待状态”则是在等待一段时间，或者唤醒动作的发生。在程序等待进入同步区域的时候，线程将进入这种状态。
        结束（Terminated）：已终止线程的线程状态，线程已经结束执行

#3、Thread的基本操作
    新建线程
        extends Thread
        implements Runnable
    终止线程
        Thread 的 stop 方法
            stop（）方法过于暴力，强行把执行到一半的线程终止，可能会引起一些数据不一致的问题 。
            最好自己终止线程
    线程中断
        线程中断并不会使线程立即退出
        Thread.interrupt()
        public boolean Thread. is Interrupted()//判断是否被中断
        public static boolean Thread . interrupted()//判断是否被中断，并清除当前中断状态
        
        需要配合中断处理程序使用
            if(Thread.currentThread().isinterrupted ()) {
                System.out.println (”Interruted!”);
                break;
            }
    等待（wait）和通知（notify)
        在线程A中，调用了obj.wait方法，那么线程A就会停止继续执行，转为等待状态。
        线程A会一直等到其他线程调用 obj.notify（）方法为止。obj 对象俨然成了多个线程之间的有效通信手段 。
        
        线程调用了wait方法时候，它就会进入 object 对象的等待队列，这个队列中可能有多个线程在等待同一个
        对象，当 object.notify()方法被调用时，它就会从这个等待队列中【随机】选择一个线程，并将其唤醒。
        
        notifyAll（）方法，唤醒全部。
        
        Object.wait（）方法并不能随便调用 。必须包含在对应的synchronzied语句中，无论是 wait方法或者notify方法
        都需要首先获得目标对象的 一个监视器
        
    挂起（suspend）和继续执行（resume）线程
        已被废弃的方法
        不推荐使用 suspend（）方法去挂起线程是因为 suspend方法在导致线程暂停的同时，并不会释放任何锁资源 
        对于被挂起的线程 ， 从它的线程状态上看，居然还是 Runnable
        
        挂起和睡眠是主动的，挂起恢复需要主动完成，睡眠恢复则是自动完成的，因为睡眠有一个睡眠时间，
        睡眠时间到则恢复到就绪态。
        而阻塞是被动的，是在等待某种事件或者资源的表现，一旦获得所需资源或者事件信息就自动回到就绪态。
    等待线程结束（join ）和谦让（ yeild)
        join 加入
            join（）方法的本质是让调用线程 wait（）方法在当前线程对象实例上
        yield
            它会使当前线程让出 CPU，回到就绪状态。
#4、volatile与Java内存模型（JMM)
    可见性
    禁止指令重排
#5、分门别类的管理：线程组
    在一个系统中，如果线程数量很多 ，而且功能分配比较明确，可以将相同功能的线程放置在 同一个线程组里
    ThreadGroup
    //将线程加入到线程组  tg 就是线程组
    Thread t1 = new Thread(tg, new ThreadGroupName(), "T1");
#6、驻守后台：守护线程（ Daemon)
    守护线程是一种特殊的线程，就和它的名字一样，它是系统的守护者，在后台默默地完成一些系统性的服务，
    比如垃圾回收线程、 JIT 线程就可以理解为守护线程。
    
    当一个系统只有守护线程时候，虚拟机就会退出。
#7、线程优先级
    在 Java 中，使用 l 到 10 表示线程优先级
#8、synchronized
    关键字 synchronized 可以有多种用法，
        指定加锁对象：对给定对象加锁，进入同步代码前要获得给定对象的锁。
        作用于实例方法：相当于对当前实例加锁，进入同步代码前要获得当前实例的锁。
        作用于静态方法：相当于对当前类加锁，进入同步代码前要获得当前类的锁 
#8、隐蔽的错误
