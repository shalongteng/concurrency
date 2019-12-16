package com.slt.concurrency.example.singleton;
/**
 *@Description 饿汉模式
 *@Author
 *@Date2019/12/16 10:00
 **/
public class Singleton6 {
    //1、构造器私有化
    private Singleton6(){

    }
    //2、定义一个返回的对象 volatile：禁止指令重排
    private static volatile Singleton6 singleton;
    //3、使用静态代码块，初始化对象
    static {
        singleton = new Singleton6();
    }

    //2和3 顺序不能变，静态代码块是按照顺序初始化的
    public static Singleton6 getInstance(){
        return singleton;
    }

    public static void main(String[] args) {
        System.out.println(getInstance().hashCode());
        System.out.println(getInstance().hashCode());
    }

}
