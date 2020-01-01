#不安全的容器--》为什么不安全？

#同步容器
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

#并发容器 
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

##TreeMap->ConcurrentSkipListMap
ConcurrentSkipListMap是通过跳表实现的，而TreeMap是通过红黑树实现的。

#BlockingQueue
取元素时，如果队列为空则等待；存元素时，如果没有空间则等待；