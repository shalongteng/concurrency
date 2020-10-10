package com.slt.concurrency.myTest.join;
/**
 * @ProjectName: concurrency
 * @ClassName: Employee
 * @Author: shalongteng
 * @Description:   我们来看一下这个应用场景：假设现在公司有三个员工A,B,C，他们要开会。
 * 但是A需要等B,C准备好之后再才能开始，B,C需要同时准备。我们先用join模拟上面的场景
 * @Date: 2019-12-08 12:18
 */
public class Employee extends Thread{
    //员工姓名
    private String employeeName;
    //员工需要准备的时间
    private long time;

    public Employee(String employeeName,long time){
        this.employeeName = employeeName;
        this.time = time;
    }

    @Override
    public void run() {
        try {
            System.out.println(employeeName+ "开始准备");
            Thread.sleep(time);
            System.out.println(employeeName+" 准备完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
