package com.slt.concurrency.pattern.singleton;
/**
 *@Description 懒汉模式--》多线程不安全
 *@Author
 *@Date2019/12/16 10:00
 **/
public class Singleton2 {
    //1、构造器私有化
    private Singleton2(){

    }
    //2、定义一个返回的对象
    public static Singleton2 singleton;

    /*
     * @Description 这种情况下,多线程不安全
     * @author shalongteng
     * @date 2019/12/16 10:41
     */
    public static Singleton2 getInstance(){
        //3、判断是否为空，不为空直接返回，为空则new一个对象
        if(singleton == null){
            singleton = new Singleton2();
        }
        return singleton;
    }

}
