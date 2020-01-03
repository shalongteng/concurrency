#1、不安全的容器--》为什么不安全？

#2、同步容器
##Collections.synchronizedList(Lists.newArrayList())-->Vector
##Collections.synchronizedSet(Sets.newHashSet())
##Collections.synchronizedMap(new HashMap<>())-->hashtable
在并发编程当中，虽然同步容器类是线程安全的，但是在某些情况下可能需要额外的客户端加锁来保护复合操作。如下面一段代码：
public static Object getLast(Vector list) {
    int lastIndex = list.size() - 1;
    return list.get(lastIndex);
}

public static void deleteLast(Vector list) {
    int lastIndex = list.size() - 1;
    list.remove(lastIndex);
}
虽然Vector是线程安全的，但是获取Vector大小与获取/删除之间没有锁保护，当获得Vector大笑之后，如另外一个线程删除了Vector中的最末尾位置的元素，
则每个函数的最后一句代码执行将报错。因此，对于复合操作，需要在符合操作上用锁来保证操作的原子性：
public static Object getLast(Vector list) {
    synchronized (list) {
        int lastIndex = list.size() - 1;
        return list.get(lastIndex);
    }
}

public static void deleteLast(Vector list) {
    synchronized (list) {
        int lastIndex = list.size() - 1;
        list.remove(lastIndex);
    }
}

#3、并发容器 
大致分四种，
##CopyOnWrite容器->copyOnWriteArrayList
每当修改容器是都会复制底层数组，这需要一定的开销，特别是当容器的规模较大时。
所以，建议仅当迭代操作远远多余修改操作时，才应该使用“写入时复制”容器。

当我们往一个容器添加元素的时候，不直接往当前容器添加，而是先将当前容器进行Copy，复制出一个新的容器，
然后向新的容器里添加元素，添加完元素之后，再将原容器的引用指向新的容器。这样做的好处是我们可以对CopyOnWrite容器进行并发的读，
而不需要加锁，因为在当前读的容器中不会添加任何元素。所以CopyOnWrite容器是一种【读写分离】的思想，读和写对应不同的容器。
##CopyOnWrite容器->CopyOnWriteArraySet

它是线程安全的无序的集合，可以将它理解成线程安全的HashSet

CopyOnWriteArraySet不能有重复集合。因此，CopyOnWriteArrayList额外提供了addIfAbsent()
和addAllAbsent()这两个添加元素的API，通过这些API来添加元素时，只有当元素不存在时才执行添加操作

##ConcurrentMap->ConcurrentHashMap（Hashtable）

Hashtable实现同步是利用synchronized关键字进行锁定的，锁住整张表让线程独占，浪费。

ConcurrentHashMap单独锁住每一个桶（segment）ConcurrentHashMap将哈希表分为16个桶（默认值），
诸如get(),put(),remove()等常用操作只锁当前需要用到的桶,有些方法需要跨段，比如size()，
就需要按照顺序锁定所有的段，完成操作后，再按顺序释放锁。。原来只能一个线程进入，
现在却能同时接受16个写线程并发进入（一定数量的写线程可以并发地修改Map，而读线程几乎不受限制）。

ConcurrentHashMap使用了快速失败迭代器（fast-fail iterator）弱一致迭代器。在这种迭代方式中，
当iterator被创建后集合再发生改变就不再是抛出ConcurrentModificationException，
取而代之的是在改变时 【实例化出新的数据】从而不影响原有的数据，iterator完成后再将头指针替换为新的数据，
这样iterator线程可以使用原来老的数据，而写线程也可以并发的完成改变，这保证了多个线程并发执行的连续性和扩展性

##ConcurrentMap->ConcurrentSkipListMap
ConcurrentSkipListMap是有序的哈希表，适用于高并发的场景。

跳表（SkipList）是一种随机化的数据结构，通过“空间来换取时间”的一个算法，建立多级索引，
实现以二分查找遍历一个有序链表。时间复杂度等同于红黑树，O(log n)。但实现却远远比红黑树要简单，

#4、BlockingQueue阻塞队列  https://www.jianshu.com/p/5d7e5b088a40
常用阻塞队列特性总结：通过加锁实现安全地读写（size、contains函数也是加锁读），通过condition或者AtomicInteger来协调生产和消费
阻塞队列解决的问题：在一个容量有限的仓库里面，实现满了就挂起生产线程，空了就挂起消费线程的兼顾性能和安全的数据结构
##BlockingQueue->ArrayBlockingQueue 
1把锁，无法并发写，2个condition，通过await、signal来实现线程调度。基于定长数组的有界队列

##BlockingQueue->LinkedBlockQueue
2把锁，支持生产、消费并发执行，但是不支持并发的生产，通过AtomicInteger和CAS来控制库存。可以指定大小，默认无界队列。
LinkedBlockQueue内部是基于单向链表的队列，可以设置capacity来实现有界队列，也可以不设置，默认是无界队列。
无界队列的时候，put、add、offer就是一样的啊，因为没有限制，所以插入肯定成功.


##BlockingQueue->SynchronousQueue
没有容量的队列，put、take成为一对儿才可以执行，否则阻塞，add和remove如果没有配对成功，直接报错。用于快速响应的业务场景
##BlockingQueue->PriorityBlockingQueue
有优先级的队列，插入元素实现Compare接口，内部维护了一个最小堆，一把锁，基于数组，自动扩容的无界队列
##BlockingQueue->DelayQueue
内部有一个PriorityBlockingQueue，最先到期的元素放在堆顶。
里面的元素必须要实现Delayed接口