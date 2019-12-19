package com.slt.concurrency.pattern.singleton;
/**
 *@Description 饿汉模式
 *@Author
 *@Date2019/12/16 10:00
 **/
public class Singleton {
    //1、构造器私有化
    private Singleton(){

    }
    //2、定义一个返回的对象
    public static Singleton singleton = new Singleton();

    public static Singleton getInstance(){
        return singleton;
    }

}
