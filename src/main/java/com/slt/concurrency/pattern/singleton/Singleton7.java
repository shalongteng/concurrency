package com.slt.concurrency.pattern.singleton;
/**
 *@Description 懒汉模式--》枚举模式
 *@Author
 *@Date2019/12/16 10:00
 **/
public class Singleton7 {
    //1、构造器私有化
    private Singleton7(){

    }


    public static Singleton7 getInstance(){
        return Singelton.INSTANCE.getInstance();
    }

    public enum Singelton{
        INSTANCE;

        private Singleton7 singleton;

        // JVM保证这个方法绝对只调用一次
        private Singelton(){
            singleton = new Singleton7();
        }

        public  Singleton7 getInstance(){
            return singleton;
        }

    }
}
