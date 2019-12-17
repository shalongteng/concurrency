#线程封闭

#ThreadLocal是什么
　　当使用ThreadLocal维护变量时，ThreadLocal为每个使用该变量的线程提供独立的变量副本，所以每一个线程都可以独立地改变自己的副本，
而不会影响其它线程所对应的副本。

　　从线程的角度看，目标变量就象是线程的本地变量，这也是类名中“Local”所要表达的意思。

ThreadLocal类接口很简单，只有4个方法

void set(Object value)设置当前线程的线程局部变量的值。
public Object get()该方法返回当前线程所对应的线程局部变量。
public void remove()将当前线程局部变量的值删除，目的是为了减少内存的占用。需要指出的是，当线程结束后，
对应该线程的局部变量将自动被垃圾回收，所以显式调用该方法清除线程的局部变量并不是必须的操作，但它可以加快内存回收的速度。

protected Object initialValue()返回该线程局部变量的初始值，该方法是一个protected的方法，显然是为了让子类覆盖而设计的。
这个方法是一个延迟调用方法，在线程第1次调用get()或set(Object)时才执行，并且仅执行1次。ThreadLocal中的缺省实现直接返回一个null。

　　ThreadLocal是如何做到为每一个线程维护变量的副本的呢？其实实现的思路很简单：在ThreadLocal类中有一个Map，
用于存储每一个线程的变量副本，Map中元素的键为线程对象，而值对应线程的变量副本。
#ThreadLocal应用
Spring使用ThreadLocal解决线程安全问题。通常只有无状态的Bean才可以在多线程环境下共享，在Spring中，
绝大部分Bean都可以声明为singleton作用域。就是因为Spring对一些Bean中非线程安全的“状态性对象”采用ThreadLocal进行封装，
让它们也成为线程安全的“状态性对象”，因此有状态的Bean就能够以singleton的方式在多线程中正常工作了。
一般的Web应用划分为控制层、服务层和持久层三个层次，在不同的层中编写对应的逻辑，下层通过接口向上层开放功能调用。

在一般情况下，从接收请求到返回响应所经过的所有程序调用都同属于一个线程。这样用户就可以根据需要，
将一些非线程安全的变量以ThreadLocal存放，在同一次请求响应的调用线程中，所有对象所访问的同一ThreadLocal变量都是当前线程所绑定的。
```java
// 非线程安全
public class TopicDao {
   //1、一个非线程安全的变量
   private Connection conn; 
   public void addTopic(){
        //②引用非线程安全变量
       Statement stat = conn.createStatement();
       //…
   }
}
```
由于1处的conn是成员变量，因为addTopic()方法是非线程安全的，必须在使用时创建一个新TopicDao实例（这样就成了非singleton）。
下面使用ThreadLocal对conn这个非线程安全的“状态”进行改造：
```java
import java.sql.Connection;
import java.sql.Statement;
public class TopicDao {

  //1、使用ThreadLocal保存Connection变量
private static ThreadLocal<Connection> connThreadLocal = new ThreadLocal<>();
public static Connection getConnection(){
        //2、如果connThreadLocal没有本线程对应的Connection创建一个新的Connection，
        //并将其保存到线程本地变量中。
    if (connThreadLocal.get() == null) {
            Connection conn = ConnectionManager.getConnection();
            connThreadLocal.set(conn);
            return conn;
        }else{
              //3、直接返回线程本地变量
            return connThreadLocal.get();
        }
    }
    public void addTopic() {
        //4、从ThreadLocal中获取线程对应的
         Statement stat = getConnection().createStatement();
    }
}
```
不同的线程在使用TopicDao时，先判断connThreadLocal.get()是否为null，如果为null，则说明
当前线程还没有对应的Connection对象，这时创建一个Connection对象并添加到本地线程变量中；如果不为null，
则说明当前的线程已经拥有了Connection对象，直接使用就可以了。这样，就保证了不同的线程使用线程相关的Connection，
而不会使用其他线程的Connection。因此，这个TopicDao就可以做到singleton共享了。 当然，这个例子本身很粗糙，
将Connection的ThreadLocal直接放在Dao只能做到本Dao的多个方法共享Connection时不发生线程安全问题，但无法和其他Dao
共用同一个Connection，要做到同一事务多Dao共享同一个Connection，必须在一个共同的外部类使用ThreadLocal保存Connection。
但这个实例基本上说明了Spring对有状态类线程安全化的解决思路。

#数据库连接池
注意pool.getConnection()，都是先从threadlocal里面拿的，如果threadlocal里面有，则用，保证线程里的多个dao操作，
用的是同一个connection，以保证事务。

如果新线程，则将新的connection放在threadlocal里，再get给到线程。


