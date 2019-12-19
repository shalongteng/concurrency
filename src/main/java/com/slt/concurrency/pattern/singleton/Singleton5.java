package com.slt.concurrency.pattern.singleton;
/**
 *@Description 懒汉模式-->多线程不安全
 *@Author
 *@Date2019/12/16 10:00
 **/
public class Singleton5 {
    //1、构造器私有化
    private Singleton5(){

    }
    //2、定义一个返回的对象 volatile：禁止指令重排
    private static volatile Singleton5 singleton;

    /*
     * @Description 这种情况下,多线程不安全
     * @author shalongteng
     * @date 2019/12/16 10:41
     */
    public static Singleton5 getInstance(){
        //3、判断是否为空，不为空直接返回，为空则new一个对象
        if(singleton == null){
            synchronized (Singleton5.class){
                if(singleton == null){
                    singleton = new Singleton5();
                }
            }
        }
        return singleton;
    }

}
