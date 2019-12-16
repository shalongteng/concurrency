package com.slt.concurrency.example.singleton;
/**
 *@Description 懒汉模式-->多线程安全
 *@Author
 *@Date2019/12/16 10:00
 **/
public class Singleton3 {
    //1、构造器私有化
    private Singleton3(){

    }
    //2、定义一个返回的对象
    private static Singleton3 singleton;

    /*
     * @Description 这种情况下,多线程安全,但是效率低下
     * @author shalongteng
     * @date 2019/12/16 10:41
     */
    public static synchronized Singleton3 getInstance(){
        //3、判断是否为空，不为空直接返回，为空则new一个对象
        if(singleton == null){
            singleton = new Singleton3();
        }
        return singleton;
    }

}
