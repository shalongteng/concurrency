package com.slt.concurrency.pattern.singleton;
/**
 *@Description 懒汉模式-->多线程不安全
 *@Author      double check
 *@Date2019/12/16 10:00
 **/
public class Singleton4 {
    //1、构造器私有化
    private Singleton4(){

    }
    //2、定义一个返回的对象
    private static Singleton4 singleton;

    /*
     * @Description 这种情况下,多线程不安全。多个线程访问，jvm会有指令重排
     * @author shalongteng
     * @date 2019/12/16 10:41
     */
    public static  Singleton4 getInstance(){
        //3、判断是否为空，不为空直接返回，为空则new一个对象
        if(singleton == null){
            synchronized (Singleton4.class){
                if(singleton == null){
                    singleton = new Singleton4();
                }
            }
        }
        return singleton;
    }

}
