package com.slt.concurrency;

public class Test {
    public static void main(String[] args) {
        int a = 10;
        int varNum = 66;
        varNum = varNum++;
        System.out.println(varNum);
    }
}
