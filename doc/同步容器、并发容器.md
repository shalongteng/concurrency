#不安全的容器--》为什么不安全？

#同步容器

#并发容器 juc
##ArrayList->copyOnWriteArrayList
写时复制容器，即copy-on-write,在多线程环境下，写时效率低，读时效率高，适合写少读多的环境。

当我们往一个容器添加元素的时候，不直接往当前容器添加，而是先将当前容器进行Copy，复制出一个新的容器，
然后向新的容器里添加元素，添加完元素之后，再将原容器的引用指向新的容器。这样做的好处是我们可以对CopyOnWrite容器进行并发的读，
而不需要加锁，因为在当前读的容器中不会添加任何元素。所以CopyOnWrite容器是一种【读写分离】的思想，读和写对应不同的容器。


##HashMap->ConcurrentHashMap
ConcurrentHashMap相比Hashtable能够进一步提高并发性

Hashtable实现同步是利用synchronized关键字进行锁定的，每次锁住整张表让线程独占，在线程安全的背后是巨大的浪费。
ConcurrentHashMap和Hashtable主要区别就是围绕着锁的粒度进行区别以及如何区锁定。

ConcurrentHashMap的实现方式，单独锁住每一个桶（segment）.ConcurrentHashMap将哈希表分为16个桶（默认值），
诸如get(),put(),remove()等常用操作只锁当前需要用到的桶,而size()才锁定整张表。原来只能一个线程进入，
现在却能同时接受16个写线程并发进入（写线程需要锁定，而读线程几乎不受限制），并发性的提升是显而易见的。

ConcurrentHashMap使用了不同于传统集合的快速失败迭代器（fast-fail iterator）的另一种迭代方式，
称为弱一致迭代器。在这种迭代方式中，当iterator被创建后集合再发生改变就不再是抛出ConcurrentModificationException，
取而代之的是在改变时 【实例化出新的数据】从而不影响原有的数据，iterator完成后再将头指针替换为新的数据，
这样iterator线程可以使用原来老的数据，而写线程也可以并发的完成改变，更重要的，这保证了多个线程并发执行的连续性和扩展性
，是性能提升的关键。

##HashSet->CopyOnWriteSet
它是线程安全的无序的集合，可以将它理解成线程安全的HashSet
HashSet是通过HashMap实现的，而CopyOnWriteArraySet则是通过CopyOnWriteArrayList实现的，并不是散列表。

CopyOnWriteArraySet具有以下特性：
1. 它最适合于具有以下特征的应用程序：Set 大小通常保持很小，只读操作远多于可变操作，需要在遍历期间防止线程间的冲突。
2. 因为通常需要复制整个基础数组，所以可变操作（add()、set() 和 remove() 等等）的开销很大。
3. 迭代器支持hasNext(), next()等不可变操作，但不支持可变 remove()等 操作。
4. 使用迭代器进行遍历的速度很快，并且不会与其他线程发生冲突。在构造迭代器时，迭代器依赖于不变的数组快照。
##TreeMap->ConcurrentSkipListMap
ConcurrentSkipListMap是通过跳表实现的，而TreeMap是通过红黑树实现的。

#skiplist ----跳表