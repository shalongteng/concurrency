#前言 https://blog.csdn.net/TimHeath/article/details/71643008
AQS类还有公平锁和非公平锁，现在就可以正式介绍一下JUC锁家族其中的一个成员——ReentrantLock
#ReentrantLock
ReentrantLock是一个互斥锁，也是一个可重入锁（Reentrant就是再次进入的意思）。ReentrantLock锁在同一个时间点只能
被一个线程锁持有，但是它可以被单个线程多次获取，每获取一次AQS的state就加1，每释放一次state就减1。还记得synchronized嘛，
它也是可重入的，一个同步方法调用另外一个同步方法是没有问题的。

在使用上无非就是获取锁和释放锁，我们完全可以用它来实现synchronized的功能

我要实现一个程序，由两条线程去输出100到0，下面是有问题的程序代码

#Condition
到了这里就要谈到Condition了，它需要与 Lock 联合使用，它的作用就是代替Object的那些监视器方法，
Condition 中的await()、signal()和signalAll()方法分别对应着Object的wait()、notify()和notifyAll()方法。

不过一个它比较牛逼的一点是，一个Lock可以关联多个Condition，这样子玩起来就很灵活了，
想要各个方法按什么顺序执行都行。还是上面那个例子，我想让两个线程和谐点，你输出一个数，然后我又输出下一个数，
这样子交替执行，实现代码如下
```java
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        Counter counter = new Counter();

        new Thread(new Runnable() {

            @Override
            public void run() {
                while (counter.getCount() >= 0) {
                    counter.desc1();
                }
            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                while (counter.getCount() >= 0) {
                    counter.desc2();
                }
            }
        }).start();
    }
}

class Counter {
    private int count = 100;
    private Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    boolean state = true;

    public void desc1() {
        lock.lock();// 上锁

        try {
            while (state)
                condition1.await();// 线程等待

            if (count >= 0) {
                System.out.println(Thread.currentThread().getName() + "--->" + count);
                count--;
            }
            state = true;// 改变状态
            condition2.signal();// 唤醒调用了condition2.await()线程

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();// 释放锁
        }
    }

    public void desc2() {
        lock.lock();// 上锁

        try {
            while (!state)
                condition2.await();// 线程等待

            if (count >= 0) {
                System.out.println(Thread.currentThread().getName() + "--->" + count);
                count--;
            }
            state = false;// 改变状态
            condition1.signal();// 唤醒调用了condition1.await()线程

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();// 释放锁
        }
    }

    public int getCount() {
        return count;
    }
}

```
最后要提一下的是ReentrantLock有两个构造方法，默认的构造方法会让它成为一个非公平锁，而如果你想创建一个公平锁则用
ReentrantLock(boolean fair)

