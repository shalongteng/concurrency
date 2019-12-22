#1.ReentrantLock
jdk中独占锁的实现除了使用关键字synchronized外,还可以使用ReentrantLock。虽然在性能上ReentrantLock
和synchronized没有什么区别，但ReentrantLock功能更加丰富，使用起来更为灵活，也更适合复杂的并发场景
#2. ReentrantLock和synchronized的相同点

##2.1 ReentrantLock是独占锁且可重入的
```java
public class ReentrantLockTest {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        for (int i = 1; i <= 3; i++) {
            lock.lock();
        }

        for(int i=1;i<=3;i++){
            try {

            } finally {
                lock.unlock();
            }
        }
    }
}
```
代码通过lock()方法先获取锁三次，然后通过unlock()方法释放锁3次，程序可以正常退出。ReentrantLock是可以重入的锁,
当一个线程获取锁时,还可以接着重复获取多次。在加上ReentrantLock的的独占性，我们可以得出以下ReentrantLock
和synchronized的相同点。

1.ReentrantLock和synchronized都是独占锁,只允许线程互斥的访问临界区。但是实现上两者不同:
synchronized加锁解锁的过程是隐式的,用户不用手动操作,优点是操作简单，但显得不够灵活。一般并发场景使用
synchronized的就够了；ReentrantLock需要手动加锁和解锁,且解锁的操作尽量要放在finally代码块中,保证线程正确释放锁。
ReentrantLock操作较为复杂，但是因为可以手动控制加锁和解锁过程,在复杂的并发场景中能派上用场。

2.ReentrantLock和synchronized都是可重入的。synchronized因为可重入因此可以放在被递归执行的方法上,
且不用担心线程最后能否正确释放锁；而ReentrantLock在重入时要却确保重复获取锁的次数必须和重复释放锁的次数一样，
否则可能导致其他线程无法获得该锁。

#3. ReentrantLock相比synchronized的额外功能

##3.1 ReentrantLock可以实现公平锁。
公平锁是指当锁可用时,在锁上等待时间最长的线程将获得锁的使用权。而非公平锁则随机分配这种使用权。
和synchronized一样，默认的ReentrantLock实现是非公平锁,因为相比公平锁，非公平锁性能更好。
当然公平锁能防止饥饿,某些情况下也很有用。在创建ReentrantLock的时候通过传进参数true创建公平锁,
如果传入的是false或没传参数则创建的是非公平锁
```java
public class ReentrantLockTest {
    //公平锁
//    static Lock lock = new ReentrantLock(true);
    //非公平锁
    static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 5; i++) {
            new Thread(new ThreadDemo(i)).start();
        }

    }

    static class ThreadDemo implements Runnable {
        Integer id;

        public ThreadDemo(Integer id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 2; i++) {
                lock.lock();
                System.out.println("获得锁的线程：" + id);
                lock.unlock();
            }
        }
    }
}
```
公平锁结果

获得锁的线程：3
获得锁的线程：0
获得锁的线程：1
获得锁的线程：2
获得锁的线程：4
获得锁的线程：3
获得锁的线程：0
获得锁的线程：1
获得锁的线程：2
获得锁的线程：4

我们开启5个线程,让每个线程都获取释放锁两次。为了能更好的观察到结果,在每次获取锁前让线程休眠20毫秒。
可以看到线程几乎是轮流的获取到了锁。如果我们改成非公平锁,再看下结果

获得锁的线程：2
获得锁的线程：2
获得锁的线程：0
获得锁的线程：0
获得锁的线程：4
获得锁的线程：4
获得锁的线程：1
获得锁的线程：1
获得锁的线程：3
获得锁的线程：3

线程会重复获取锁。如果申请获取锁的线程足够多,那么可能会造成某些线程长时间得不到锁。这就是非公平锁的“饥饿”问题。

##3.2 .ReentrantLock可响应中断
当使用synchronized实现锁时,阻塞在锁上的线程除非获得锁否则将一直等待下去，也就是说这种无限等待获取锁的
行为无法被中断。而ReentrantLock给我们提供了一个可以响应中断的获取锁的方法lockInterruptibly()。
该方法可以用来解决死锁问题。
```java
public class ReentrantLockTest {
    static Lock lock1 = new ReentrantLock();
    static Lock lock2 = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(new ThreadDemo(lock1, lock2));//该线程先获取锁1,再获取锁2
        Thread thread1 = new Thread(new ThreadDemo(lock2, lock1));//该线程先获取锁2,再获取锁1
        thread.start();
        thread1.start();
        thread.interrupt();//是第一个线程中断
    }

    static class ThreadDemo implements Runnable {
        Lock firstLock;
        Lock secondLock;
        public ThreadDemo(Lock firstLock, Lock secondLock) {
            this.firstLock = firstLock;
            this.secondLock = secondLock;
        }
        @Override
        public void run() {
            try {
                firstLock.lockInterruptibly();
                TimeUnit.MILLISECONDS.sleep(10);//更好的触发死锁
                secondLock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                firstLock.unlock();
                secondLock.unlock();
                System.out.println(Thread.currentThread().getName()+"正常结束!");
            }
        }
    }
}
```

构造死锁场景:创建两个子线程,子线程在运行时会分别尝试获取两把锁。其中一个线程先获取锁1在获取锁2，
另一个线程正好相反。如果没有外界中断，该程序将处于死锁状态永远无法停止。我们通过使其中一个线程中断，
来结束线程间毫无意义的等待。被中断的线程将抛出异常，而另一个线程将能获取锁后正常结束。

##3.3 获取锁时限时等待

ReentrantLock还给我们提供了获取锁限时等待的方法tryLock(),可以选择传入时间参数,表示等待指定的时间,
无参则表示立即返回锁申请的结果:true表示获取锁成功,false表示获取锁失败。我们可以使用该方法配合失败重试机制
来更好的解决死锁问题。

#4. 结合Condition实现等待通知机制
Condition由ReentrantLock对象创建,并且可以同时创建多个

static Condition notEmpty = lock.newCondition();
static Condition notFull = lock.newCondition();

Condition接口在使用前必须先调用ReentrantLock的lock()方法获得锁。之后调用Condition接口的await()将释放锁,
并且在该Condition上等待,直到有其他线程调用Condition的signal()方法唤醒线程。使用方式和wait,notify类似。


