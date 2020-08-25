#Atomic
##线程安全：
当多个线程访问某个类时，不管运行时环境采用何种调度方式或者这些进程将如何交替执行，并且在主调代码中不需要任何额外的同步或协调，
这个类都能表现出正确的行为，那么就称这个类时线程安全的。

#AtomicInteger
是对int类型的一个封装，提供原子性的访问和更新操作，其原子性操作的实现是基于CAS（compare-and-swap）技术。
所谓CAS，表现为一组指令，当利用CAS执行试图进行一些更新操作时。会首先比较当前数值，如果数值未变，代表没有其它线程进行并发修改，
则成功更新。如果数值改变，则可能出现不同的选择，要么进行重试，要么就返回是否成功。也就是所谓的“乐观锁”。

从AtomicInteger的内部属性可以看出，它依赖于Unsafe提供的一些底层能力；以volatile的value字段，记录数值，以保证可见性。

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

为什么要引入LongAdder？
我们知道，AtomicLong是利用了底层的CAS操作来提供并发性的，比如addAndGet方法：

```java

public class AtomicLong extends Number implements java.io.Serializable {

 public final long getAndIncrement() {
        return unsafe.getAndAddLong(this, valueOffset, 1L);
    }
}
```

    上述方法调用了Unsafe类的getAndAddLong方法，该方法是个native方法，它的逻辑是采用自旋的方式不断更新目标值，直到更新成功。

   在并发量较低的环境下，线程冲突的概率比较小，自旋的次数不会很多。但是，高并发环境下，N个线程同时进行自旋操作，会出现大量失败并不断自旋的情况，
此时AtomicLong的自旋会成为瓶颈。

这就是LongAdder引入的初衷——解决高并发环境下AtomicLong的自旋瓶颈问题。

#AtomicReference  
    而AtomicReference提供了以无锁方式访问共享资源的能力，看看如何通过AtomicReference保证线程安全，来看个具体的例子：
    
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
从注释来看，就是在native函数里，判断当前对象和期望的expect（也就是前面的current）是否一致，
如果一致则返回true，然后更新成20；
#AtomicIntegerFieldUpdater
利用原子性去更新某个类的实例
它可以更新某个类中指定成员变量的值。注意：修改的成员变量需要用volatile关键字来修饰，
并且不能是static描述的字段

#AtomicStampReference
这个类它的核心是要解决CAS的ABA问题（CAS操作的时候，其他线程将变量的值A改成了B，接着又改回了A，
等线程使用期望值A与当前变量进行比较的时候，发现A变量没有变，于是CAS就将A值进行了交换操作。
实际上该值已经被其他线程改变过）。ABA问题的解决思路就是每次变量变更的时候，就将版本号加一。
看一下它的一个核心方法compareAndSet()

#AtomicLongArray

它维护了一个数组。在该数组下，我们可以选择性的已原子性操作更新某个索引对应的值

