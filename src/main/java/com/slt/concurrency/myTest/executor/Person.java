package com.slt.concurrency.myTest.executor;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Data
public class Person {
    private String name;
    private BigDecimal salary;
    private Long num;

    public Person(String name,BigDecimal salary,Long num){
        this.name=name;
        this.salary=salary;
        this.num=num;
    }

    public BigDecimal getSumSalary(){
        return this.salary.multiply(new BigDecimal(this.num));
    }

    public static void main(String[] args) {
        List<Person> pList = new ArrayList<>();
        pList.add(new Person("张三", new BigDecimal("10.00"),6L));
        pList.add(new Person("王五", new BigDecimal("20.00"),2L));
        pList.add(new Person("李四", new BigDecimal("30.00"),3L));
        pList.add(new Person("王五", new BigDecimal("40.00"),4L));
        pList.add(new Person("赵六", new BigDecimal("50.00"),5L));

        BigDecimal sumReduce = pList.stream().map(Person::getSumSalary).reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("工资总计：" + sumReduce);
    }
}
