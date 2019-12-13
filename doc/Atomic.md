#Atomic
##线程安全：
当多个线程访问某个类时，不管运行时环境采用何种调度方式或者这些进程将如何交替执行，并且在主调代码中不需要任何额外的同步或协调，
这个类都能表现出正确的行为，那么就称这个类时线程安全的。

##线程安全主要体现在以下三个方面：
原子性：提供了互斥访问，同一时刻只能有一个线程对它进行操作

可见性：一个线程对主内存的修改可以及时的被其他线程观察到

有序性：一个线程观察其他线程中的指令执行顺序，由于指令重排序的存在，该观察结果一般杂乱无序

#AtomicInteger
是对int类型的一个封装，提供原子性的访问和更新操作，其原子性操作的实现是基于CAS（compare-and-swap）技术。
所谓CAS，表现为一组指令，当利用CAS执行试图进行一些更新操作时。会首先比较当前数值，如果数值未变，代表没有其它线程进行并发修改，
则成功更新。如果数值改变，则可能出现不同的选择，要么进行重试，要么就返回是否成功。也就是所谓的“乐观锁”。

从AtomicInteger的内部属性可以看出，它依赖于Unsafe提供的一些底层能力；以volatile的value字段，记录数值，以保证可见性。

CAS算法的过程是这样的，它包含三个参数CAS（V,E,N）。V表示要更新的变量，E表示预期值，N表示新值。仅当V值等于E值时，才会将V的值设为N。
如果V值和E值不同，说明已经有了其他线程做了修改，则当前线程什么都不做。最后，CAS返回当前V的真实值。CAS是抱着乐观的态度来的，
它总认为自己可以完成。当有多个线程竞争时，总有一个线程能够完成。其他线程则继续尝试，直到任务完成。CAS操作即使没有锁，
也能发现其他线程对当前线程的干扰，并进行正确处理。


我们查看下incrementAndGet()方法，它的第一个参数为对象本身，第二个参数为valueOffset是用来记录value本身在内存的编译地址的
这个记录，也主要是为了在更新操作在内存中找到value的位置，方便比较，第三个参数为常量1。
```java
public class AtomicInteger extends Number implements java.io.Serializable {
    private static final long serialVersionUID = 6214790243416807050L;
 
    // setup to use Unsafe.compareAndSwapInt for updates
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long valueOffset;
 
    static {
        try {
            valueOffset = unsafe.objectFieldOffset
                (AtomicInteger.class.getDeclaredField("value"));
        } catch (Exception ex) { throw new Error(ex); }
    }
 
    private volatile int value;
 
 
    //... 此处省略多个方法...
 
    /**
     * Atomically increments by one the current value.
     *
     * @return the updated value
     */
    public final int incrementAndGet() {
        return unsafe.getAndAddInt(this, valueOffset, 1) + 1;
    }
}
```

AtomicInteger源码里使用了一个Unsafe的类,它提供了一个getAndAddInt的方法，我们继续点看查看它的源码：

```java

public final class Unsafe {
    private static final Unsafe theUnsafe;
 
    //....此处省略很多方法及成员变量....
 public final int getAndAddInt(Object var1, long var2, int var4) {
        int var5;
        do {
            var5 = this.getIntVolatile(var1, var2);
        } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));

        return var5;
 }
 
 
 public final native boolean compareAndSwapInt(Object var1, long var2, int var4, int var5);
 
 public native int getIntVolatile(Object var1, long var2);
}

```
可以看到这里使用了一个do while语句来做主体实现的。而在while语句里它的核心是调用了一个compareAndSwapInt()的方法。
它是一个native方法，是一个底层的方法。

假设我们要执行0+1=0的操作，下面是单线程情况下各参数的值

compareAndSwapInt()方法的第一个参数（var1）是当前的对象，就是代码示例中的count。此时它的值为0（期望值）。
第二个值（var2）是传递的valueOffset值，它的值为12。第三个参数（var4）就为常量1。方法中的变量参数（var5）是根据
参数一和参数二valueOffset，调用底层getIntVolatile方法得到的值，此时它的值为0 。compareAndSwapInt()想要达到的目标
是对于count这个对象，如果当前的期望值var1里的value跟底层的返回的值（var5）相同的话，那么把它更新成var5+var4这个值。
不同的话重新循环取期望值（var5）直至当前值与期望值相同才做更新。compareAndSwap方法的核心也就是我们通常所说的CAS

#AtomicLong

Atomic包下其他的类如AtomicLong等的实现原理基本与上述一样。

#LongAdder
这里再介绍下LongAdder这个类，通过上述的分析，我们已经知道了AtomicLong使用CAS：在一个死循环内不断尝试修改目标值直到修改成功。如果在竞争不激烈的情况下，它修改成功概率很高。反之，如果在竞争激烈的情况下，修改失败的概率会很高，它就会进行多次的循环尝试，因此性能会受到一些影响。

对于普通类型的long和double变量，jvm允许将64位的读操作或写操作拆成两个32位的操作。LongAdder的核心思想是将热点数据分离，它可以将AtomicLong内部核心数据value分离成一个数组，每个线程访问时通过hash等算法映射到其中一个数字进行计数。而最终的计数结果则为这个数组的求和累加，其中热点数据value，它会被分离成多个单元的cell，每个cell独自维护内部的值,当前对象的实际值由所有的cell累计合成。这样,热点就进行了有效的分离,提高了并行度。LongAdder相当于在AtomicLong的基础上将单点的更新压力分散到各个节点上，在低并发的时候对base的直接更新可以很好的保障跟Atomic的性能基本一致。而在高并发的时候，通过分散提高了性能。但是如果在统计的时候有并发更新，可能会导致统计的数据有误差。

#AtomicReference  
##compareAndSet
```java
@Slf4j
public class AtomicExample4 {
 
    private static AtomicReference<Integer> count = new AtomicReference<>(0);
 
    public static void main(String[] args) {
        count.compareAndSet(0, 20); 
        count.compareAndSet(0, 1); 
        log.info("count:{}", count.get());
    }
}
```
从注释来看，就是在native函数里，判断当前对象和期望的expect（也就是前面的current）是否一致，如果一致则返回true，然后更新成20；：
