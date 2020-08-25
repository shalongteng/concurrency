#前言
JUC包中的锁的功能更加强大，它提供了各种各样的锁（公平锁，非公平锁，共享锁，独占锁……），所以使用起来很灵活。
#Lock
Lock接口就是JUC中锁的顶级接口，支持语义不同的锁规则，比如说公平锁和非公平锁，独占锁（也可以叫互斥锁）和共享锁等。

它最主要的两个方法就是lock()和unlock()，一看就知道是获取锁和释放锁。

还有一个比较有趣的方法是boolean tryLock(long time, TimeUnit unit)方法，
它在尝试获取锁，获取到了就返回true，一段时间获取不到就算了，返回一个false。

当然了，还有Condition newCondition()方法，返回与之关联的Condition对象
#AbstractQueuedSynchronizer->AQS
这个类就是JUC锁的根基，是JUC中不同锁的共同抽象类，锁的许多公共方法都是在这个类中实现的

无论是带有Lock后缀的类如ReentrantLock还是不带Lock后缀的类如Semaphore它们里面的内部类Sync都继承了AQS。
其实JUC中的各种锁只是一个表面装饰，它们里面真正实现功能的还是Sync。

/**
 * The synchronization state.
 */
private volatile int state;

JUC中所有锁的获取锁和释放锁操作都是围绕着这个同步状态state进行加减操作。每次一个线程获取到锁即对应着这个state加1，
释放锁就对应着这个state减1（Semaphore就特殊点，它能加n和减n），state为0的时候代表锁空闲。

#公平锁和非公平锁
很多锁的名叫Sync的内部类继承了AQS类，而这个Sync又分别有FairSync和NonfairSync类继承它，看名字就知道它们分别是公平锁和非公平锁了。

CLH队列是AQS中“等待锁”的线程队列，比如说独占锁被一个线程占用着，其它线程就必须等待咯，这些线程就是在CLH队列等待的。这个队列的是用链表实现的，
你可以看到AQS类有个内部类Node，这个就是链表的节点。CLH是通过自旋+CAS保证节点插入和移除的原子性（这个我在介绍原子类的时候说过类似的），
就是说往里面插入或移除一个节点的时候，在并发条件下不会有问题。

至于CLH是什么意思，在Node的注释上有"CLH" (Craig, Landin, and Hagersten) lock queue，目测是三个人的名字缩写，应该是造出它的人吧。

所谓公平，就是大家排队，是按照CLH队列先来先得的规则，即使锁没被任何线程持有，只要一个线程不是处于队头，它也会乖乖地等，
公平地获取锁；非公平，那就是插队咯，当线程要获取锁时，它会无视CLH等待队列而直接获取锁，如果锁没有被任何线程持有，那不管它在CLH的那个角落，它都直接获取锁，没什么道德可言。
#AbstractQueuedLongSynchronizer和AbstractOwnableSynchronizer

 至于AbstractQueuedLongSynchronizer类跟AbstractQueuedSynchronizer类差不多，只不过它里面的state是long类型的，
 而AbstractQueuedSynchronizer的是int类型的，而且它们都继承了AbstractOwnableSynchronizer类——
 一个记录当前持有独占锁的线程的抽象类，这个类很简单，维护一个Thread类型变量，这个应该就是记录当前持有独占锁的线程，
 然后提供它的getter和setter方法，它虽然是一个抽象类，但是里面并没有抽象方法。