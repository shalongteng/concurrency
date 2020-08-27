#1、进程，线程
    进程是系统进行【资源分配】和【调度】的基本单位
    线程就是轻量级进程，是程序执行的最小单位，线程间的切换和调度的成本远远小于进程 
#2、线程的状态
    线程的所有状态都在 Thread 中的 State 枚举中 定义，如下所示 ：
        NEW ,
        RUNNABLE ,
        BLOCKED,
        WAITING ,
        TIMED WAITING ,
        TERMINATED;
    NEW 状态表示刚刚创建的钱程 ， 这种线程还没开始执行 。
    等到线程的 start（）方法调用时，才表示线程开始执行 。 当线程执行时，处于 RUNNABLE 状态 ，
    线程在执行过程 中 遇到 了 synchronized 同步块 ， 就会进入BLOCKED 阻塞状态，
    WAITING 会进入一个无时间限制的等待 ，
    TIMED WAITING 会进行一个有时限 的等待
    当线程执 行完 毕 后 ， 则进入TERMINATED 状态 ， 表示结束 。
    
    WAITING 的线程正是在等待一些特殊的事件 。 比如，通过 wait（）方法等待的线程在
    等待 notify（）方法 ， 而通过 join（）方法等待的线程则会等待 目标线程的终止 。
    
#1、线程的基本操作
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
#1、
#1、
